package test.sun.nio.cs;
/*
 * Copyright (c) 2008, Oracle and/or its affiliates. All rights reserved.
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

/* @test
   @bug 4634811
   @summary Check Unicode 2.1 --> Big5-HKSCS reverse compatible mappings
 */

/*
 * Mappings sourced from www.info.gov.hk/digital21/eng/hkscs
 */

public class TestUni2HKSCS {
    private static final String uni21String =
        "\uF3F5\uF3F8\uF3FD\uF403\uF413\uF415\uF418\uF419\uF41A"+
        "\uF424\uF426\uF428\uF42B\uF42C\uF42D\uF437\uF439"+
        "\uF43A\uF43C\uF445\uF44A\uF44E\uF44F\uF464\uF468"+
        "\uF46A\uF46B\uF473\uF47A\uF487\uF489\uF493\uF494"+
        "\uF496\uF49D\uF4A2\uF4AB\uF4AC\uF4AE\uF4B5\uF4C6"+
        "\uF4CB\uF4D6\uF4E1\uF4FA\uF502\uF504\uF51E\uF529"+
        "\uF52D\uF52E\uF530\uF536\uF540\uF544\uF554\uF637"+
        "\uF638\uF63B\uF63C\uF641\uF642\uF643\uF644\uF648"+
        "\uF64C\uF651\uF653\uF658\uF65D\uF65F\uF662\uF664"+
        "\uF666\uF669\uF66C\uF66D\uF66E\uF675\uF677\uF67C"+
        "\uF67E\uF688\uF68D\uF69D\uF6A1\uF6A2\uF6A5\uF6A7"+
        "\uF6AE\uF6AF\uE316\uE326\uE32A\uE33C\uE351\uE35E"+
        "\uE368\uE376\uE378\uE387\uE39C\uE3AD\uE3B3\uE3BE"+
        "\uE3C2\uE3C7\uE3CB\uE3D1\uE3D2\uE3DE\uE3E2\uE3E4"+
        "\uE3E8\uE3EA\uE3F5\uE3F7\uE406\uE40E\uE427\uE437"+
        "\uE43D\uE449\uE44F\uE460\uE46B\uE47A\uE493\uE4A0"+
        "\uE4A5\uE4A9\uE4B2\uE4BE\uE4BF\uE4C0\uE4DE\uE4E3"+
        "\uE4E5\uE4ED\uE4F7\uE500\uE507\uE50E\uE534\uE539"+
        "\uE53A\uE53B\uE53D\uE541\uE553\uE569\uE574\uE578"+
        "\uE57B\uE57F\uE591\uE596\uE597\uE598\uE59C\uE5AE"+
        "\uE5B9\uE5BC\uE5E0\uE5EC\uE5F5\uE5FA\uE5FE\uE60C"+
        "\uE61D\uE629\uE62D\uE639\uE63B\uE63D\uE651\uE664"+
        "\uE675\uE681\uE682\uE68D\uE694\uE69D\uE6A0\uE6A4"+
        "\uE6A9\uE6AB\uE6B6\uE6C8\uE6E2\uE6E3\uE6E8\uE6ED"+
        "\uE6EF\uE6F1\uE6F4\uE6F8\uE6FD\uE6FE\uE700\uE712"+
        "\uE716\uE719\uE726\uE72E\uE730\uE738\uE73A\uE73B"+
        "\uE749\uE74C\uE760\uE776\uE77E\uE780\uE78F\uE7AA"+
        "\uE7AC\uE7AD\uE7AE\uE7C0\uE7C1\uE7C7\uE7CB\uE7D0"+
        "\uE7D5\uE7D9\uE7E6\uE7EA\uE7F0\uE7F6\uE7FA\uE806"+
        "\uE815\uE81B\uE81D\uE822\uE824\uE82E\uE831\uE832"+
        "\uE83D\uE83F\uE850\uE853\uE85F\uE86B\uE86D\uE876"+
        "\uE880\uE88E\uE899\uE89B\uE89C\uE8B3\uE8B7\uE8BC"+
        "\uE8D2\uE8E2\uE8E4\uE8EC\uE8EE\uE8F4\uE8F6\uE8F7"+
        "\uE8F9\uE8FA\uE8FD\uE901\uE906\uE90B\uE90E\uE90F"+
        "\uE910\uE911\uE912\uE915\uE91B\uE931\uE932\uE946"+
        "\uE949\uE965\uE96C\uE979\uE98D\uE994\uE996\uE99C"+
        "\uE99D\uE99E\uE99F\uE9A1\uE9A2\uE9A7\uE9AA\uE9AC"+
        "\uE9AE\uE9B5\uE9B7\uE9B9\uE9BA\uE9BC\uE9BD\uE9C5"+
        "\uE9CF\uE9D0\uE9D6\uE9D8\uE9DB\uE9DE\uE9E5\uE9E6"+
        "\uE9E9\uE9EB\uE9F7\uE9FE\uEA03\uEA0A\uEA0B\uEA0F"+
        "\uEA13\uEA15\uEA1B\uEA21\uEA2A\uEA31\uEA35\uEA3C"+
        "\uEA3D\uEA4C\uEA4F\uEA57\uEA63\uEA66\uEA75\uEA76"+
        "\uEA7D\uEA82\uEA85\uEA87\uEA89\uEA8D\uEA90\uEA9B"+
        "\uEA9D\uEAA6\uEAA8\uEAC8\uEAF7\uEB17\uEB51\uEB52"+
        "\uEB60\uEB66\uEB6A\uEB87\uEB8B\uEB92\uEB96\uEBB1"+
        "\uEBC3\uEBD6\uEBE0\uEBE6\uEBEA\uEBFF\uEC00\uEC03"+
        "\uEC0B\uEC0D\uEC16\uEC3D\uEC4A\uEC4D\uEC53\uEC55"+
        "\uEC61\uEC77\uEC7C\uEC7F\uEC87\uEC8C\uEC8F\uEC97"+
        "\uECAE\uECB0\uECD0\uECD5\uECF4\uED05\uED1B\uED1F"+
        "\uED23\uED33\uED37\uED38\uED3A\uED44\uED48\uED4C"+
        "\uED59\uED5C\uED5D\uED5E\uED64\uED66\uED6D\uED6F"+
        "\uED7B\uEDAE\uEDC1\uEDF3\uEDF5\uEE2E\uEE43\uEE45"+
        "\uEE4A\uEE53\uEE59\uEE65\uEE89\uEE93\uEE97\uEE9F"+
        "\uEEB2\uF6DD\uF820\uF821\uF822\uF823\uF824\uF825"+
        "\uF826\uF827\uF828\uF829\uF82A\uF82B\uF82C\uF82D"+
        "\uF82E\uF82F\uF830\uF831\uF832\uF833\uF834\uF835"+
        "\uF836\uF837\uF838\uF839\uF83A\uF83B\uE005\uE008"+
        "\uE028\uE02B\uE043\uE06A\uE06B\uE06E\uE07E\uE086"+
        "\uE098\uE09B\uE0A0\uE0A8\uE0BB\uE0C2\uE0CD\uE0D7"+
        "\uE0DA\uE0F1\uE0F4\uE0FA\uE0FC\uE104\uE105\uE10E"+
        "\uE117\uE125\uE12A\uE12B\uE13C\uE143\uE144\uE153"+
        "\uE15E\uE15F\uE160\uE164\uE17E\uE18C\uE19A\uE19C"+
        "\uE1A7\uE1A9\uE1C6\uE1CF\uE1E0\uE1E1\uE1E3\uE1E7"+
        "\uE1EA\uE1F4\uE1F8\uE203\uE211\uE217\uE218\uE239"+
        "\uE23F\uE243\uE246\uE25D\uE25E\uE281\uE28A\uE298"+
        "\uE2A2\uE2AC\uE2B1\uE2C8\uE2D7\uE30D";

    private static final byte[] expectedBytes =
        {(byte)0x89,(byte)0xb7,(byte)0x89,(byte)0xba,(byte)0x89,
        (byte)0xbf,(byte)0x89,(byte)0xc5,(byte)0x89,(byte)0xd5,
        (byte)0x89,(byte)0xd7,(byte)0x89,(byte)0xda,(byte)0x89,
        (byte)0xdb,(byte)0x89,(byte)0xdc,(byte)0x89,(byte)0xe6,
        (byte)0x89,(byte)0xe8,(byte)0x89,(byte)0xea,(byte)0x89,
        (byte)0xed,(byte)0x89,(byte)0xee,(byte)0x89,(byte)0xef,
        (byte)0x89,(byte)0xf9,(byte)0x89,(byte)0xfb,(byte)0x89,
        (byte)0xfc,(byte)0x89,(byte)0xfe,(byte)0x8a,(byte)0x48,
        (byte)0x8a,(byte)0x4d,(byte)0x8a,(byte)0x51,(byte)0x8a,
        (byte)0x52,(byte)0x8a,(byte)0x67,(byte)0x8a,(byte)0x6b,
        (byte)0x8a,(byte)0x6d,(byte)0x8a,(byte)0x6e,(byte)0x8a,
        (byte)0x76,(byte)0x8a,(byte)0x7d,(byte)0x8a,(byte)0xac,
        (byte)0x8a,(byte)0xae,(byte)0x8a,(byte)0xb8,(byte)0x8a,
        (byte)0xb9,(byte)0x8a,(byte)0xbb,(byte)0x8a,(byte)0xc2,
        (byte)0x8a,(byte)0xc7,(byte)0x8a,(byte)0xd0,(byte)0x8a,
        (byte)0xd1,(byte)0x8a,(byte)0xd3,(byte)0x8a,(byte)0xda,
        (byte)0x8a,(byte)0xeb,(byte)0x8a,(byte)0xf0,(byte)0x8a,
        (byte)0xfb,(byte)0x8b,(byte)0x47,(byte)0x8b,(byte)0x60,
        (byte)0x8b,(byte)0x68,(byte)0x8b,(byte)0x6a,(byte)0x8b,
        (byte)0xa6,(byte)0x8b,(byte)0xb1,(byte)0x8b,(byte)0xb5,
        (byte)0x8b,(byte)0xb6,(byte)0x8b,(byte)0xb8,(byte)0x8b,
        (byte)0xbe,(byte)0x8b,(byte)0xc8,(byte)0x8b,(byte)0xcc,
        (byte)0x8b,(byte)0xdc,(byte)0x8d,(byte)0x63,(byte)0x8d,
        (byte)0x64,(byte)0x8d,(byte)0x67,(byte)0x8d,(byte)0x68,
        (byte)0x8d,(byte)0x6d,(byte)0x8d,(byte)0x6e,(byte)0x8d,
        (byte)0x6f,(byte)0x8d,(byte)0x70,(byte)0x8d,(byte)0x74,
        (byte)0x8d,(byte)0x78,(byte)0x8d,(byte)0x7d,(byte)0x8d,
        (byte)0xa1,(byte)0x8d,(byte)0xa6,(byte)0x8d,(byte)0xab,
        (byte)0x8d,(byte)0xad,(byte)0x8d,(byte)0xb0,(byte)0x8d,
        (byte)0xb2,(byte)0x8d,(byte)0xb4,(byte)0x8d,(byte)0xb7,
        (byte)0x8d,(byte)0xba,(byte)0x8d,(byte)0xbb,(byte)0x8d,
        (byte)0xbc,(byte)0x8d,(byte)0xc3,(byte)0x8d,(byte)0xc5,
        (byte)0x8d,(byte)0xca,(byte)0x8d,(byte)0xcc,(byte)0x8d,
        (byte)0xd6,(byte)0x8d,(byte)0xdb,(byte)0x8d,(byte)0xeb,
        (byte)0x8d,(byte)0xef,(byte)0x8d,(byte)0xf0,(byte)0x8d,
        (byte)0xf3,(byte)0x8d,(byte)0xf5,(byte)0x8d,(byte)0xfc,
        (byte)0x8d,(byte)0xfd,(byte)0x8e,(byte)0x45,(byte)0x8e,
        (byte)0x55,(byte)0x8e,(byte)0x59,(byte)0x8e,(byte)0x6b,
        (byte)0x8e,(byte)0xa2,(byte)0x8e,(byte)0xaf,(byte)0x8e,
        (byte)0xb9,(byte)0x8e,(byte)0xc7,(byte)0x8e,(byte)0xc9,
        (byte)0x8e,(byte)0xd8,(byte)0x8e,(byte)0xed,(byte)0x8e,
        (byte)0xfe,(byte)0x8f,(byte)0x45,(byte)0x8f,(byte)0x50,
        (byte)0x8f,(byte)0x54,(byte)0x8f,(byte)0x59,(byte)0x8f,
        (byte)0x5d,(byte)0x8f,(byte)0x63,(byte)0x8f,(byte)0x64,
        (byte)0x8f,(byte)0x70,(byte)0x8f,(byte)0x74,(byte)0x8f,
        (byte)0x76,(byte)0x8f,(byte)0x7a,(byte)0x8f,(byte)0x7c,
        (byte)0x8f,(byte)0xa9,(byte)0x8f,(byte)0xab,(byte)0x8f,
        (byte)0xba,(byte)0x8f,(byte)0xc2,(byte)0x8f,(byte)0xdb,
        (byte)0x8f,(byte)0xeb,(byte)0x8f,(byte)0xf1,(byte)0x8f,
        (byte)0xfd,(byte)0x90,(byte)0x44,(byte)0x90,(byte)0x55,
        (byte)0x90,(byte)0x60,(byte)0x90,(byte)0x6f,(byte)0x90,
        (byte)0xaa,(byte)0x90,(byte)0xb7,(byte)0x90,(byte)0xbc,
        (byte)0x90,(byte)0xc0,(byte)0x90,(byte)0xc9,(byte)0x90,
        (byte)0xd5,(byte)0x90,(byte)0xd6,(byte)0x90,(byte)0xd7,
        (byte)0x90,(byte)0xf5,(byte)0x90,(byte)0xfa,(byte)0x90,
        (byte)0xfc,(byte)0x91,(byte)0x45,(byte)0x91,(byte)0x4f,
        (byte)0x91,(byte)0x58,(byte)0x91,(byte)0x5f,(byte)0x91,
        (byte)0x66,(byte)0x91,(byte)0xae,(byte)0x91,(byte)0xb3,
        (byte)0x91,(byte)0xb4,(byte)0x91,(byte)0xb5,(byte)0x91,
        (byte)0xb7,(byte)0x91,(byte)0xbb,(byte)0x91,(byte)0xcd,
        (byte)0x91,(byte)0xe3,(byte)0x91,(byte)0xee,(byte)0x91,
        (byte)0xf2,(byte)0x91,(byte)0xf5,(byte)0x91,(byte)0xf9,
        (byte)0x92,(byte)0x4c,(byte)0x92,(byte)0x51,(byte)0x92,
        (byte)0x52,(byte)0x92,(byte)0x53,(byte)0x92,(byte)0x57,
        (byte)0x92,(byte)0x69,(byte)0x92,(byte)0x74,(byte)0x92,
        (byte)0x77,(byte)0x92,(byte)0xbd,(byte)0x92,(byte)0xc9,
        (byte)0x92,(byte)0xd2,(byte)0x92,(byte)0xd7,(byte)0x92,
        (byte)0xdb,(byte)0x92,(byte)0xe9,(byte)0x92,(byte)0xfa,
        (byte)0x93,(byte)0x47,(byte)0x93,(byte)0x4b,(byte)0x93,
        (byte)0x57,(byte)0x93,(byte)0x59,(byte)0x93,(byte)0x5b,
        (byte)0x93,(byte)0x6f,(byte)0x93,(byte)0xa4,(byte)0x93,
        (byte)0xb5,(byte)0x93,(byte)0xc1,(byte)0x93,(byte)0xc2,
        (byte)0x93,(byte)0xcd,(byte)0x93,(byte)0xd4,(byte)0x93,
        (byte)0xdd,(byte)0x93,(byte)0xe0,(byte)0x93,(byte)0xe4,
        (byte)0x93,(byte)0xe9,(byte)0x93,(byte)0xeb,(byte)0x93,
        (byte)0xf6,(byte)0x94,(byte)0x49,(byte)0x94,(byte)0x63,
        (byte)0x94,(byte)0x64,(byte)0x94,(byte)0x69,(byte)0x94,
        (byte)0x6e,(byte)0x94,(byte)0x70,(byte)0x94,(byte)0x72,
        (byte)0x94,(byte)0x75,(byte)0x94,(byte)0x79,(byte)0x94,
        (byte)0x7e,(byte)0x94,(byte)0xa1,(byte)0x94,(byte)0xa3,
        (byte)0x94,(byte)0xb5,(byte)0x94,(byte)0xb9,(byte)0x94,
        (byte)0xbc,(byte)0x94,(byte)0xc9,(byte)0x94,(byte)0xd1,
        (byte)0x94,(byte)0xd3,(byte)0x94,(byte)0xdb,(byte)0x94,
        (byte)0xdd,(byte)0x94,(byte)0xde,(byte)0x94,(byte)0xec,
        (byte)0x94,(byte)0xef,(byte)0x95,(byte)0x44,(byte)0x95,
        (byte)0x5a,(byte)0x95,(byte)0x62,(byte)0x95,(byte)0x64,
        (byte)0x95,(byte)0x73,(byte)0x95,(byte)0xb0,(byte)0x95,
        (byte)0xb2,(byte)0x95,(byte)0xb3,(byte)0x95,(byte)0xb4,
        (byte)0x95,(byte)0xc6,(byte)0x95,(byte)0xc7,(byte)0x95,
        (byte)0xcd,(byte)0x95,(byte)0xd1,(byte)0x95,(byte)0xd6,
        (byte)0x95,(byte)0xdb,(byte)0x95,(byte)0xdf,(byte)0x95,
        (byte)0xec,(byte)0x95,(byte)0xf0,(byte)0x95,(byte)0xf6,
        (byte)0x95,(byte)0xfc,(byte)0x96,(byte)0x41,(byte)0x96,
        (byte)0x4d,(byte)0x96,(byte)0x5c,(byte)0x96,(byte)0x62,
        (byte)0x96,(byte)0x64,(byte)0x96,(byte)0x69,(byte)0x96,
        (byte)0x6b,(byte)0x96,(byte)0x75,(byte)0x96,(byte)0x78,
        (byte)0x96,(byte)0x79,(byte)0x96,(byte)0xa6,(byte)0x96,
        (byte)0xa8,(byte)0x96,(byte)0xb9,(byte)0x96,(byte)0xbc,
        (byte)0x96,(byte)0xc8,(byte)0x96,(byte)0xd4,(byte)0x96,
        (byte)0xd6,(byte)0x96,(byte)0xdf,(byte)0x96,(byte)0xe9,
        (byte)0x96,(byte)0xf7,(byte)0x97,(byte)0x43,(byte)0x97,
        (byte)0x45,(byte)0x97,(byte)0x46,(byte)0x97,(byte)0x5d,
        (byte)0x97,(byte)0x61,(byte)0x97,(byte)0x66,(byte)0x97,
        (byte)0x7c,(byte)0x97,(byte)0xae,(byte)0x97,(byte)0xb0,
        (byte)0x97,(byte)0xb8,(byte)0x97,(byte)0xba,(byte)0x97,
        (byte)0xc0,(byte)0x97,(byte)0xc2,(byte)0x97,(byte)0xc3,
        (byte)0x97,(byte)0xc5,(byte)0x97,(byte)0xc6,(byte)0x97,
        (byte)0xc9,(byte)0x97,(byte)0xcd,(byte)0x97,(byte)0xd2,
        (byte)0x97,(byte)0xd7,(byte)0x97,(byte)0xda,(byte)0x97,
        (byte)0xdb,(byte)0x97,(byte)0xdc,(byte)0x97,(byte)0xdd,
        (byte)0x97,(byte)0xde,(byte)0x97,(byte)0xe1,(byte)0x97,
        (byte)0xe7,(byte)0x97,(byte)0xfd,(byte)0x97,(byte)0xfe,
        (byte)0x98,(byte)0x53,(byte)0x98,(byte)0x56,(byte)0x98,
        (byte)0x72,(byte)0x98,(byte)0x79,(byte)0x98,(byte)0xa8,
        (byte)0x98,(byte)0xbc,(byte)0x98,(byte)0xc3,(byte)0x98,
        (byte)0xc5,(byte)0x98,(byte)0xcb,(byte)0x98,(byte)0xcc,
        (byte)0x98,(byte)0xcd,(byte)0x98,(byte)0xce,(byte)0x98,
        (byte)0xd0,(byte)0x98,(byte)0xd1,(byte)0x98,(byte)0xd6,
        (byte)0x98,(byte)0xd9,(byte)0x98,(byte)0xdb,(byte)0x98,
        (byte)0xdd,(byte)0x98,(byte)0xe4,(byte)0x98,(byte)0xe6,
        (byte)0x98,(byte)0xe8,(byte)0x98,(byte)0xe9,(byte)0x98,
        (byte)0xeb,(byte)0x98,(byte)0xec,(byte)0x98,(byte)0xf4,
        (byte)0x98,(byte)0xfe,(byte)0x99,(byte)0x40,(byte)0x99,
        (byte)0x46,(byte)0x99,(byte)0x48,(byte)0x99,(byte)0x4b,
        (byte)0x99,(byte)0x4e,(byte)0x99,(byte)0x55,(byte)0x99,
        (byte)0x56,(byte)0x99,(byte)0x59,(byte)0x99,(byte)0x5b,
        (byte)0x99,(byte)0x67,(byte)0x99,(byte)0x6e,(byte)0x99,
        (byte)0x73,(byte)0x99,(byte)0x7a,(byte)0x99,(byte)0x7b,
        (byte)0x99,(byte)0xa1,(byte)0x99,(byte)0xa5,(byte)0x99,
        (byte)0xa7,(byte)0x99,(byte)0xad,(byte)0x99,(byte)0xb3,
        (byte)0x99,(byte)0xbc,(byte)0x99,(byte)0xc3,(byte)0x99,
        (byte)0xc7,(byte)0x99,(byte)0xce,(byte)0x99,(byte)0xcf,
        (byte)0x99,(byte)0xde,(byte)0x99,(byte)0xe1,(byte)0x99,
        (byte)0xe9,(byte)0x99,(byte)0xf5,(byte)0x99,(byte)0xf8,
        (byte)0x9a,(byte)0x48,(byte)0x9a,(byte)0x49,(byte)0x9a,
        (byte)0x50,(byte)0x9a,(byte)0x55,(byte)0x9a,(byte)0x58,
        (byte)0x9a,(byte)0x5a,(byte)0x9a,(byte)0x5c,(byte)0x9a,
        (byte)0x60,(byte)0x9a,(byte)0x63,(byte)0x9a,(byte)0x6e,
        (byte)0x9a,(byte)0x70,(byte)0x9a,(byte)0x79,(byte)0x9a,
        (byte)0x7b,(byte)0x9a,(byte)0xbd,(byte)0x9a,(byte)0xec,
        (byte)0x9b,(byte)0x4d,(byte)0x9b,(byte)0xa9,(byte)0x9b,
        (byte)0xaa,(byte)0x9b,(byte)0xb8,(byte)0x9b,(byte)0xbe,
        (byte)0x9b,(byte)0xc2,(byte)0x9b,(byte)0xdf,(byte)0x9b,
        (byte)0xe3,(byte)0x9b,(byte)0xea,(byte)0x9b,(byte)0xee,
        (byte)0x9c,(byte)0x4a,(byte)0x9c,(byte)0x5c,(byte)0x9c,
        (byte)0x6f,(byte)0x9c,(byte)0x79,(byte)0x9c,(byte)0xa1,
        (byte)0x9c,(byte)0xa5,(byte)0x9c,(byte)0xba,(byte)0x9c,
        (byte)0xbb,(byte)0x9c,(byte)0xbe,(byte)0x9c,(byte)0xc6,
        (byte)0x9c,(byte)0xc8,(byte)0x9c,(byte)0xd1,(byte)0x9c,
        (byte)0xf8,(byte)0x9d,(byte)0x46,(byte)0x9d,(byte)0x49,
        (byte)0x9d,(byte)0x4f,(byte)0x9d,(byte)0x51,(byte)0x9d,
        (byte)0x5d,(byte)0x9d,(byte)0x73,(byte)0x9d,(byte)0x78,
        (byte)0x9d,(byte)0x7b,(byte)0x9d,(byte)0xa5,(byte)0x9d,
        (byte)0xaa,(byte)0x9d,(byte)0xad,(byte)0x9d,(byte)0xb5,
        (byte)0x9d,(byte)0xcc,(byte)0x9d,(byte)0xce,(byte)0x9d,
        (byte)0xee,(byte)0x9d,(byte)0xf3,(byte)0x9e,(byte)0x53,
        (byte)0x9e,(byte)0x64,(byte)0x9e,(byte)0x7a,(byte)0x9e,
        (byte)0x7e,(byte)0x9e,(byte)0xa4,(byte)0x9e,(byte)0xb4,
        (byte)0x9e,(byte)0xb8,(byte)0x9e,(byte)0xb9,(byte)0x9e,
        (byte)0xbb,(byte)0x9e,(byte)0xc5,(byte)0x9e,(byte)0xc9,
        (byte)0x9e,(byte)0xcd,(byte)0x9e,(byte)0xda,(byte)0x9e,
        (byte)0xdd,(byte)0x9e,(byte)0xde,(byte)0x9e,(byte)0xdf,
        (byte)0x9e,(byte)0xe5,(byte)0x9e,(byte)0xe7,(byte)0x9e,
        (byte)0xee,(byte)0x9e,(byte)0xf0,(byte)0x9e,(byte)0xfc,
        (byte)0x9f,(byte)0x70,(byte)0x9f,(byte)0xa5,(byte)0x9f,
        (byte)0xd7,(byte)0x9f,(byte)0xd9,(byte)0xa0,(byte)0x53,
        (byte)0xa0,(byte)0x68,(byte)0xa0,(byte)0x6a,(byte)0xa0,
        (byte)0x6f,(byte)0xa0,(byte)0x78,(byte)0xa0,(byte)0x7e,
        (byte)0xa0,(byte)0xac,(byte)0xa0,(byte)0xd0,(byte)0xa0,
        (byte)0xda,(byte)0xa0,(byte)0xde,(byte)0xa0,(byte)0xe6,
        (byte)0xa0,(byte)0xf9,(byte)0xc6,(byte)0xcd,(byte)0xc8,
        (byte)0xd6,(byte)0xc8,(byte)0xd7,(byte)0xc8,(byte)0xd8,
        (byte)0xc8,(byte)0xd9,(byte)0xc8,(byte)0xda,(byte)0xc8,
        (byte)0xdb,(byte)0xc8,(byte)0xdc,(byte)0xc8,(byte)0xdd,
        (byte)0xc8,(byte)0xde,(byte)0xc8,(byte)0xdf,(byte)0xc8,
        (byte)0xe0,(byte)0xc8,(byte)0xe1,(byte)0xc8,(byte)0xe2,
        (byte)0xc8,(byte)0xe3,(byte)0xc8,(byte)0xe4,(byte)0xc8,
        (byte)0xe5,(byte)0xc8,(byte)0xe6,(byte)0xc8,(byte)0xe7,
        (byte)0xc8,(byte)0xe8,(byte)0xc8,(byte)0xe9,(byte)0xc8,
        (byte)0xea,(byte)0xc8,(byte)0xeb,(byte)0xc8,(byte)0xec,
        (byte)0xc8,(byte)0xed,(byte)0xc8,(byte)0xee,(byte)0xc8,
        (byte)0xef,(byte)0xc8,(byte)0xf0,(byte)0xc8,(byte)0xf1,
        (byte)0xfa,(byte)0x45,(byte)0xfa,(byte)0x48,(byte)0xfa,
        (byte)0x68,(byte)0xfa,(byte)0x6b,(byte)0xfa,(byte)0xa5,
        (byte)0xfa,(byte)0xcc,(byte)0xfa,(byte)0xcd,(byte)0xfa,
        (byte)0xd0,(byte)0xfa,(byte)0xe0,(byte)0xfa,(byte)0xe8,
        (byte)0xfa,(byte)0xfa,(byte)0xfa,(byte)0xfd,(byte)0xfb,
        (byte)0x43,(byte)0xfb,(byte)0x4b,(byte)0xfb,(byte)0x5e,
        (byte)0xfb,(byte)0x65,(byte)0xfb,(byte)0x70,(byte)0xfb,
        (byte)0x7a,(byte)0xfb,(byte)0x7d,(byte)0xfb,(byte)0xb6,
        (byte)0xfb,(byte)0xb9,(byte)0xfb,(byte)0xbf,(byte)0xfb,
        (byte)0xc1,(byte)0xfb,(byte)0xc9,(byte)0xfb,(byte)0xca,
        (byte)0xfb,(byte)0xd3,(byte)0xfb,(byte)0xdc,(byte)0xfb,
        (byte)0xea,(byte)0xfb,(byte)0xef,(byte)0xfb,(byte)0xf0,
        (byte)0xfc,(byte)0x42,(byte)0xfc,(byte)0x49,(byte)0xfc,
        (byte)0x4a,(byte)0xfc,(byte)0x59,(byte)0xfc,(byte)0x64,
        (byte)0xfc,(byte)0x65,(byte)0xfc,(byte)0x66,(byte)0xfc,
        (byte)0x6a,(byte)0xfc,(byte)0xa6,(byte)0xfc,(byte)0xb4,
        (byte)0xfc,(byte)0xc2,(byte)0xfc,(byte)0xc4,(byte)0xfc,
        (byte)0xcf,(byte)0xfc,(byte)0xd1,(byte)0xfc,(byte)0xee,
        (byte)0xfc,(byte)0xf7,(byte)0xfd,(byte)0x49,(byte)0xfd,
        (byte)0x4a,(byte)0xfd,(byte)0x4c,(byte)0xfd,(byte)0x50,
        (byte)0xfd,(byte)0x53,(byte)0xfd,(byte)0x5d,(byte)0xfd,
        (byte)0x61,(byte)0xfd,(byte)0x6c,(byte)0xfd,(byte)0x7a,
        (byte)0xfd,(byte)0xa2,(byte)0xfd,(byte)0xa3,(byte)0xfd,
        (byte)0xc4,(byte)0xfd,(byte)0xca,(byte)0xfd,(byte)0xce,
        (byte)0xfd,(byte)0xd1,(byte)0xfd,(byte)0xe8,(byte)0xfd,
        (byte)0xe9,(byte)0xfe,(byte)0x4d,(byte)0xfe,(byte)0x56,
        (byte)0xfe,(byte)0x64,(byte)0xfe,(byte)0x6e,(byte)0xfe,
        (byte)0x78,(byte)0xfe,(byte)0x7d,(byte)0xfe,(byte)0xb6,
        (byte)0xfe,(byte)0xc5,(byte)0xfe,(byte)0xfb };

    public static void main(String[] args) throws Exception {
        byte[] encodedBytes = new byte[1000];

        encodedBytes = uni21String.getBytes("Big5-HKSCS");

        for (int i = 0; i < encodedBytes.length - 1; i++) {
            if (encodedBytes[i] != expectedBytes[i]
                || encodedBytes[i+1] != expectedBytes[i+1]) {
           throw new Exception("Unexpected char->byte HKSCS mappings");
            }
        }
    }
}