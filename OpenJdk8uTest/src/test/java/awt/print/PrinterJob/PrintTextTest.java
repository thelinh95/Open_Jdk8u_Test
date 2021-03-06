package test.java.awt.print.PrinterJob;
/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */


/**
 * @test
 * @bug 6425068 7157659 8132890
 * @summary Confirm that text prints where we expect to the length we expect.
 * @run applet/manual=yesno PrintTextTest.html
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import test.java.awt.*;
import test.java.awt.event.*;
import test.java.awt.font.*;
import test.java.awt.geom.*;
import test.java.awt.print.*;
import test.java.text.*;
import test.java.util.*;
import test.javax.swing.*;

public class PrintTextTest extends JApplet {
    public void start() {
        StandalonePrintTextTest.main(null);
    }
}

class StandalonePrintTextTest extends Component implements Printable {

    static int preferredSize;
    Font textFont;
    AffineTransform gxTx;
    String page;
    boolean useFM;

    public static void main(String args[]) {

        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat portrait = pjob.defaultPage();
        portrait.setOrientation(PageFormat.PORTRAIT);
        preferredSize = (int)portrait.getImageableWidth();

        PageFormat landscape = pjob.defaultPage();
        landscape.setOrientation(PageFormat.LANDSCAPE);

        Book book = new Book();

        JTabbedPane p = new JTabbedPane();

        int page = 1;
        Font font = new Font("Dialog", Font.PLAIN, 18);
        String name = "Page " + new Integer(page++);
        StandalonePrintTextTest ptt = new StandalonePrintTextTest(name, font, null, false);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        font = new Font("Dialog", Font.PLAIN, 18);
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, font, null, true);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        font = new Font("Lucida Sans", Font.PLAIN, 18);
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, font, null, false);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        font = new Font("Lucida Sans", Font.PLAIN, 18);
        AffineTransform rotTx = AffineTransform.getRotateInstance(0.15);
        rotTx.translate(60,0);
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, font, rotTx, false);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        font = new Font("Dialog", Font.PLAIN, 18);
        AffineTransform scaleTx = AffineTransform.getScaleInstance(1.25, 1.25);
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, font, scaleTx, false);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        font = new Font("Dialog", Font.PLAIN, 18);
        scaleTx = AffineTransform.getScaleInstance(-1.25, 1.25);
        scaleTx.translate(-preferredSize/1.25, 0);
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, font, scaleTx, false);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        font = new Font("Dialog", Font.PLAIN, 18);
        scaleTx = AffineTransform.getScaleInstance(1.25, -1.25);
        scaleTx.translate(0, -preferredSize/1.25);
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, font, scaleTx, false);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        font = font.deriveFont(rotTx);
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, font, null, false);
        p.add(ptt, BorderLayout.CENTER);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        font = new Font("Monospaced", Font.PLAIN, 12);
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, font, null, false);
        p.add(ptt, BorderLayout.CENTER);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        Font xfont = font.deriveFont(AffineTransform.getScaleInstance(1.5, 1));
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, xfont, null, false);
        p.add(ptt, BorderLayout.CENTER);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        Font yfont = font.deriveFont(AffineTransform.getScaleInstance(1, 1.5));
        name = "Page " + new Integer(page++);
        ptt = new StandalonePrintTextTest(name, yfont, null, false);
        p.add(ptt, BorderLayout.CENTER);
        p.add(name, ptt);
        book.append(ptt, portrait);
        book.append(ptt, landscape);

        if (System.getProperty("os.name").startsWith("Windows")) {
            font = new Font("MS Gothic", Font.PLAIN, 12);
            name = "Page " + new Integer(page++);
            ptt = new PrintJAText(name, font, null, true);
            p.add(ptt, BorderLayout.CENTER);
            p.add(name, ptt);
            book.append(ptt, portrait);
            book.append(ptt, landscape);

            font = new Font("MS Gothic", Font.PLAIN, 12);
            name = "Page " + new Integer(page++);
            rotTx = AffineTransform.getRotateInstance(0.15);
            ptt = new PrintJAText(name, font, rotTx, true);
            p.add(ptt, BorderLayout.CENTER);
            p.add(name, ptt);
            book.append(ptt, portrait);
            book.append(ptt, landscape);
        }

        pjob.setPageable(book);

        JFrame f = new JFrame();
        f.add(BorderLayout.CENTER, p);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        f.pack();
        f.show();

        try {
            if (pjob.printDialog()) {
                pjob.print();
            }
        } catch (PrinterException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public StandalonePrintTextTest(String page, Font font, AffineTransform gxTx,
                         boolean fm) {
        this.page = page;
        textFont = font;
        this.gxTx = gxTx;
        this.useFM = fm;
        setBackground(Color.white);
    }

    public static AttributedCharacterIterator getIterator(String s) {
        return new AttributedString(s).getIterator();
    }

    static String orient(PageFormat pf) {
        if (pf.getOrientation() == PageFormat.PORTRAIT) {
            return "Portrait";
        } else {
            return "Landscape";
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {

        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(),  pf.getImageableY());
        g.drawString(page+" "+orient(pf),50,20);
        g.translate(0, 25);
        paint(g);
        return PAGE_EXISTS;
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dimension getPreferredSize() {
        return new Dimension(preferredSize, preferredSize);
    }

    public void paint(Graphics g) {

        /* fill with white before any transformation is applied */
        g.setColor(Color.white);
        g.fillRect(0, 0, getSize().width, getSize().height);


        Graphics2D g2d = (Graphics2D) g;
        if (gxTx != null) {
            g2d.transform(gxTx);
        }
        if (useFM) {
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                                 RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }

        g.setFont(textFont);
        FontMetrics fm = g.getFontMetrics();

        String s;
        int LS = 30;
        int ix=10, iy=LS+10;
        g.setColor(Color.black);

        s = "drawString(String str, int x, int y)";
        g.drawString(s, ix, iy);
        if (!textFont.isTransformed()) {
            g.drawLine(ix, iy+1, ix+fm.stringWidth(s), iy+1);
        }

        iy += LS;
        s = "drawString(AttributedCharacterIterator iterator, int x, int y)";
        g.drawString(getIterator(s), ix, iy);

        iy += LS;
        s = "\tdrawChars(\t\r\nchar[], int off, int len, int x, int y\t)";
        g.drawChars(s.toCharArray(), 0, s.length(), ix, iy);
        if (!textFont.isTransformed()) {
            g.drawLine(ix, iy+1, ix+fm.stringWidth(s), iy+1);
        }

        iy += LS;
        s = "drawBytes(byte[], int off, int len, int x, int y)";
        byte data[] = new byte[s.length()];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) s.charAt(i);
        }
        g.drawBytes(data, 0, data.length, ix, iy);

        Font f = g2d.getFont();
        FontRenderContext frc = g2d.getFontRenderContext();

        iy += LS;
        s = "drawString(String s, float x, float y)";
        g2d.drawString(s, (float) ix, (float) iy);
        if (!textFont.isTransformed()) {
            g.drawLine(ix, iy+1, ix+fm.stringWidth(s), iy+1);
        }

        iy += LS;
        s = "drawString(AttributedCharacterIterator iterator, "+
            "float x, float y)";
        g2d.drawString(getIterator(s), (float) ix, (float) iy);

        iy += LS;
        s = "drawGlyphVector(GlyphVector g, float x, float y)";
        GlyphVector gv = f.createGlyphVector(frc, s);
        g2d.drawGlyphVector(gv, ix, iy);
        Point2D adv = gv.getGlyphPosition(gv.getNumGlyphs());
        if (!textFont.isTransformed()) {
            g.drawLine(ix, iy+1, ix+(int)adv.getX(), iy+1);
        }

        iy += LS;
        s = "GlyphVector with position adjustments";

        gv = f.createGlyphVector(frc, s);
        int ng = gv.getNumGlyphs();
        adv = gv.getGlyphPosition(ng);
        for (int i=0; i<ng; i++) {
            Point2D gp = gv.getGlyphPosition(i);
            double gx = gp.getX();
            double gy = gp.getY();
            if (i % 2 == 0) {
                gy+=5;
            } else {
                gy-=5;
            }
            gp.setLocation(gx, gy);
            gv.setGlyphPosition(i, gp);
        }
        g2d.drawGlyphVector(gv, ix, iy);
        if (!textFont.isTransformed()) {
            g.drawLine(ix, iy+1, ix+(int)adv.getX(), iy+1);
        }

        iy +=LS;
        s = "drawString: \u0924\u094d\u0930 \u0915\u0948\u0930\u0947 End.";
        g.drawString(s, ix, iy);
        if (!textFont.isTransformed()) {
            g.drawLine(ix, iy+1, ix+fm.stringWidth(s), iy+1);
        }

        iy += LS;
        s = "TextLayout 1: \u0924\u094d\u0930 \u0915\u0948\u0930\u0947 End.";
        TextLayout tl = new TextLayout(s, new HashMap(), frc);
        tl.draw(g2d,  ix, iy);

        iy += LS;
        s = "TextLayout 2: \u0924\u094d\u0930 \u0915\u0948\u0930\u0947 End.";
        tl = new TextLayout(s, f, frc);
        tl.draw(g2d,  ix, iy);
    }
}

class PrintJAText extends StandalonePrintTextTest {


    public PrintJAText(String page, Font font, AffineTransform gxTx,
                         boolean fm) {
        super(page, font, gxTx, fm);
    }

    private static final String TEXT =
        "\u3042\u3044\u3046\u3048\u304a\u30a4\u30ed\u30cf" +
        "\u30cb\u30db\u30d8\u30c8\u4e00\u4e01\u4e02\u4e05\uff08";


    public void paint(Graphics g) {

        /* fill with white before any transformation is applied */
        g.setColor(Color.white);
        g.fillRect(0, 0, getSize().width, getSize().height);


        Graphics2D g2d = (Graphics2D) g;
        if (gxTx != null) {
            g2d.transform(gxTx);
        }
        if (useFM) {
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                                 RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }

        String text = TEXT + TEXT + TEXT;
        g.setColor(Color.black);
        int y = 20;
        float origSize = 7f;
        for (int i=0;i<11;i++) {
            float size = origSize+(i*0.1f);
            g2d.translate(0, size+6);
            Font f = textFont.deriveFont(size);
            g2d.setFont(f);
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(text);
            g.drawLine(0, y+1, stringWidth, y+1);
            g.drawString(text, 0, y);
            y +=10;
        }
    }
}
