package test.java.nio.file.WatchService;
/*
 * Copyright (c) 2016, Red Hat, Inc. and/or its affiliates.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

/**
 * @test
 * @bug 8153925
 * @summary Tests potential interference between a thread creating and closing
 *     a WatchService with another thread that is deleting and re-creating the
 *     directory at around the same time. This scenario tickled a timing bug
 *     in the Windows implementation.
 */

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.nio.file.StandardWatchEventKinds.*;

public class DeleteInterference {

    private static final int ITERATIONS_COUNT = 1024;

    /**
     * Execute two tasks in a thread pool. One task loops on creating and
     * closing a WatchService, the other task deletes and re-creates the
     * directory.
     */
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("work");
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            Future<?> task1 = pool.submit(() -> openAndCloseWatcher(dir));
            Future<?> task2 = pool.submit(() -> deleteAndRecreateDirectory(dir));
            task1.get();
            task2.get();
        } finally {
            pool.shutdown();
            deleteFileTree(dir);
        }
    }

    private static void openAndCloseWatcher(Path dir) {
        FileSystem fs = FileSystems.getDefault();
        for (int i = 0; i < ITERATIONS_COUNT; i++) {
            try (WatchService watcher = fs.newWatchService()) {
                dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            } catch (IOException ioe) {
                // ignore
            }
        }
    }

    private static void deleteAndRecreateDirectory(Path dir) {
        for (int i = 0; i < ITERATIONS_COUNT; i++) {
            try {
                deleteFileTree(dir);
                Path subdir = Files.createDirectories(dir.resolve("subdir"));
                Files.createFile(subdir.resolve("test"));
            } catch (IOException ioe) {
                // ignore
            }
        }
    }

    private static void deleteFileTree(Path file) {
        try {
            if (Files.isDirectory(file)) {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(file)) {
                    for (Path pa : stream) {
                        deleteFileTree(pa);
                    }
                }
            }
            Files.delete(file);
        } catch (IOException ioe) {
            // ignore
        }
    }
}
