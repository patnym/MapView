package com.onlylemi.mapview;

import android.graphics.PointF;

import com.onlylemi.mapview.library.navigation.NavMeshBuilder;
import com.onlylemi.mapview.library.navigation.Space;
import com.onlylemi.mapview.library.utils.collision.MapConvexObject;

import java.util.ArrayList;
import java.util.List;

/**
 * TestData
 *
 * @author: onlylemi
 */
public final class TestData {

    private TestData() {}

    /**
     * Represents the top left 3 shelfs in the bromma floor plan test map
     * @return
     */
    public static List<Space> getSpaceList() {
        List<Space> spaces = new ArrayList<>();
        Space s1 = new Space(new MapConvexObject(createBox(
                new PointF(22.4f, 81.0f),
                new PointF(245.4f, 140f)
        )));

        Space s2 = new Space(new MapConvexObject(createBox(
                new PointF(245.4f, 81.0f),
                new PointF(284.9f, 384.5f)
        )));

        Space s3 = new Space(new MapConvexObject(createBox(
                new PointF(22.4f, 328f),
                new PointF(245.4f, 384.5f)
        )));

        Space s4 = new Space(new MapConvexObject(createBox(
                new PointF(22.4f, 140f),
                new PointF(81.4f, 328f)
        )));

        Space s5 = new Space(new MapConvexObject(createBox(
                new PointF(81.4f, 179.5f),
                new PointF(245.4f, 226f)
        )));

        Space s6 = new Space(new MapConvexObject(createBox(
                new PointF(81.4f, 243f),
                new PointF(245.4f, 290.5f)
        )));

        NavMeshBuilder.connectSpaces(s1, s4);
        NavMeshBuilder.connectSpaces(s1, s2);

        NavMeshBuilder.connectSpaces(s4, s5);
        NavMeshBuilder.connectSpaces(s4, s6);

        NavMeshBuilder.connectSpaces(s3, s4);
        NavMeshBuilder.connectSpaces(s3, s2);

        NavMeshBuilder.connectSpaces(s2, s5);
        NavMeshBuilder.connectSpaces(s2, s6);

        spaces.add(s1);
        spaces.add(s2);
        spaces.add(s3);
        spaces.add(s4);
        spaces.add(s5);
        spaces.add(s6);

        return spaces;
    }

    /**
     * Returns a space struct pre-connected for the "navmeshed-map.png"
     * @return
     */
    public static List<Space> getNavMeshMapSpaceStruct() {
        List<Space> spaceStruct = new ArrayList<>();

        //s1
        spaceStruct.add(createBoxSpace(
            2, 9,
            124, 24
        ));
        //s2
        spaceStruct.add(createBoxSpace(
                2, 24,
                17, 347
        ));
        //s3
        spaceStruct.add(createBoxSpace(
                100, 24,
                124, 347
        ));
        //s4
        spaceStruct.add(createBoxSpace(
                91, 46,
                100, 112
        ));
        //s5
        spaceStruct.add(createBoxSpace(
                17, 46,
                91, 68
        ));
        //s6
        spaceStruct.add(createBoxSpace(
                17, 89,
                91, 112
        ));
        //s7
        spaceStruct.add(createBoxSpace(
                17, 133,
                100, 154
        ));
        //s8
        spaceStruct.add(createBoxSpace(
                17, 175,
                100, 195
        ));
        //s9
        spaceStruct.add(createBoxSpace(
                17, 216,
                100, 239
        ));
        //s10
        spaceStruct.add(createBoxSpace(
                17, 260,
                100, 285
        ));
        //s11
        spaceStruct.add(createBoxSpace(
                17, 306,
                100, 330
        ));
        //s12
        spaceStruct.add(createBoxSpace(
                124, 34,
                146, 83
        ));
        //s13
        spaceStruct.add(createBoxSpace(
                124, 291,
                146, 347
        ));
        //s14
        spaceStruct.add(createBoxSpace(
                146, 9,
                166, 347
        ));
        //s15
        spaceStruct.add(createBoxSpace(
                166, 32,
                256, 54
        ));
        //s16
        spaceStruct.add(createBoxSpace(
                256, 9,
                333, 192
        ));
        //s17
        spaceStruct.add(createBoxSpace(
                166, 78,
                256, 100
        ));
        //s18
        spaceStruct.add(createBoxSpace(
                166, 124,
                256, 146
        ));
        //s19
        spaceStruct.add(createBoxSpace(
                166, 170,
                256, 192
        ));
        //s20
        spaceStruct.add(createBoxSpace(
                275,192,
                323, 284
        ));
        //s21
        spaceStruct.add(createBoxSpace(
                166, 217,
                275, 238
        ));
        //s22
        spaceStruct.add(createBoxSpace(
                166,263,
                275,284
        ));
        //s23
        spaceStruct.add(createBoxSpace(
                166, 305,
                289, 327
        ));
        //s24
        spaceStruct.add(createBoxSpace(
                202, 327,
                239, 347
        ));
        //s25
        spaceStruct.add(createBoxSpace(
                289, 284,
                323, 327
        ));
        //s26
        spaceStruct.add(createBoxSpace(
                294, 327,
                395, 347
        ));
        //s27
        spaceStruct.add(createBoxSpace(
                323, 299,
                381, 327
        ));
        //s28
        spaceStruct.add(createBoxSpace(
                381, 304,
                395, 327
        ));
        //s29
        spaceStruct.add(createBoxSpace(
                395, 304,
                567, 338
        ));
        //s30
        spaceStruct.add(createBoxSpace(
                323,192,
                353,281
        ));
        //s31
        spaceStruct.add(createPolygon(
                new PointF(333, 155),
                new PointF(333, 192),
                new PointF(350, 192)
        ));
        //s32
        spaceStruct.add(createBoxSpace(
                347, 281,
                381, 299
        ));
        //s33
        spaceStruct.add(createPolygon(
                point(353,281),
                point(381,281),
                point(381,227),
                point(353,199)
        ));
        //s34
        spaceStruct.add(createPolygon(
                point(381,231),
                point(434, 257),
                point(434,285),
                point(381,285)
        ));
        //s35
        spaceStruct.add(createBoxSpace(
                434, 257,
                568, 285
        ));
        //s36
        spaceStruct.add(createBoxSpace(
                542, 285,
                568, 304
        ));
        //s37
        spaceStruct.add(createPolygon(
                point(461, 257),
                point(567, 257),
                point(567, 200),
                point(514, 200),
                point(461, 238)
        ));
        //s38
        spaceStruct.add(createPolygon(
                point(461, 238),
                point(514, 200),
                point(523, 184),
                point(515, 149),
                point(493, 134),
                point(470, 135),
                point(446, 160),
//                point(438, 169),
                point(396, 214),
                point(433, 235)
        ));
        //s39
        spaceStruct.add(createPolygon(
                point(438, 169),
                point(396, 214),
                point(369, 186),
                point(359, 162),
                point(398, 134)
        ));
        //s40
        spaceStruct.add(createPolygon(
                point(446, 160),
                point(470, 135),
                point(461, 119),
                point(449, 99),
                point(442, 89),
                point(405, 125)
        ));
        //Find common edges and connect
        for(int i = 0; i < spaceStruct.size(); i++) {
            for(int y = i+1; y < spaceStruct.size(); y++) {
                if(spaceStruct.get(i).findCommonEdge(
                        spaceStruct.get(y)) != null) {
                    NavMeshBuilder.connectSpaces(spaceStruct.get(i),
                            spaceStruct.get(y));
                }
            }
        }

        return spaceStruct;
    }

    private static PointF point(float x, float y) {
        return new PointF(x, y);
    }

    private static Space createPolygon(PointF... points ) {
        List<PointF> shape = new ArrayList<>();
        for (PointF point : points) {
            shape.add(point);
        }
        return new Space(new MapConvexObject(shape));
    }

    private static Space createBoxSpace(float topLeftX, float topLeftY, float botRightX, float botRightY) {
        return new Space(new MapConvexObject(createBox(
                new PointF(topLeftX, topLeftY),
                new PointF(botRightX, botRightY)
        )));
    }

    private static List<PointF> createBox(PointF topLeft, PointF botRight) {
        float width = botRight.x - topLeft.x;
        float height = botRight.y - topLeft.y;

        List<PointF> shape = new ArrayList<>();
        shape.add(new PointF(topLeft.x, topLeft.y));
        shape.add(new PointF(topLeft.x + width, topLeft.y));
        shape.add(new PointF(botRight.x, botRight.y));
        shape.add(new PointF(topLeft.x, topLeft.y + height));
        return shape;
    }

    public static List<PointF> getNodesList() {
        List<PointF> nodes = new ArrayList<>();
        nodes.add(new PointF(222, 34));
        nodes.add(new PointF(268, 34));
        nodes.add(new PointF(314, 34));
        nodes.add(new PointF(359, 34));
        nodes.add(new PointF(406, 34));
        nodes.add(new PointF(455, 34));
        nodes.add(new PointF(500, 34));
        nodes.add(new PointF(547, 34));
        nodes.add(new PointF(590, 34));
        nodes.add(new PointF(630, 34));
        nodes.add(new PointF(229, 194));
        nodes.add(new PointF(268, 194));
        nodes.add(new PointF(314, 194));
        nodes.add(new PointF(359, 194));
        nodes.add(new PointF(406, 194));
        nodes.add(new PointF(455, 194));
        nodes.add(new PointF(500, 194));
        nodes.add(new PointF(547, 194));
        nodes.add(new PointF(590, 194));
        nodes.add(new PointF(630, 194));
        nodes.add(new PointF(425, 194));
        nodes.add(new PointF(229, 260));
        nodes.add(new PointF(425, 260));
        nodes.add(new PointF(630, 260));
        nodes.add(new PointF(229, 310));
        nodes.add(new PointF(425, 310));
        nodes.add(new PointF(630, 310));
        nodes.add(new PointF(229, 360));
        nodes.add(new PointF(425, 360));
        nodes.add(new PointF(630, 360));
        nodes.add(new PointF(229, 410));
        nodes.add(new PointF(425, 410));
        nodes.add(new PointF(630, 410));
        nodes.add(new PointF(229, 460));
        nodes.add(new PointF(425, 460));
        nodes.add(new PointF(630, 460));
        nodes.add(new PointF(229, 510));
        nodes.add(new PointF(425, 510));
        nodes.add(new PointF(630, 510));
        nodes.add(new PointF(229, 560));
        nodes.add(new PointF(425, 560));
        nodes.add(new PointF(571, 560));
        nodes.add(new PointF(229, 610));
        nodes.add(new PointF(425, 610));
        nodes.add(new PointF(571, 610));
        nodes.add(new PointF(229, 670));
        nodes.add(new PointF(425, 670));
        nodes.add(new PointF(571, 670));
        nodes.add(new PointF(240, 830));
        nodes.add(new PointF(280, 830));
        nodes.add(new PointF(320, 830));
        nodes.add(new PointF(390, 830));
        nodes.add(new PointF(475, 830));
        nodes.add(new PointF(560, 830));
        nodes.add(new PointF(620, 830));
        nodes.add(new PointF(320, 760));
        nodes.add(new PointF(390, 760));
        nodes.add(new PointF(475, 760));
        nodes.add(new PointF(560, 760));
        nodes.add(new PointF(280, 670));
        nodes.add(new PointF(320, 670));
        nodes.add(new PointF(475, 670));
        nodes.add(new PointF(620, 714));
        nodes.add(new PointF(1030, 210));
        nodes.add(new PointF(700, 210));
        nodes.add(new PointF(700, 620));
        nodes.add(new PointF(1030, 620));
        nodes.add(new PointF(1290, 200));
        nodes.add(new PointF(1290, 530));
        nodes.add(new PointF(1290, 840));
        nodes.add(new PointF(1090, 840));
        nodes.add(new PointF(700, 714));
        nodes.add(new PointF(1030, 0));

        return nodes;
    }

    public static List<PointF> getNodesContactList() {
        List<PointF> nodesContact = new ArrayList<PointF>();
        nodesContact.add(new PointF(0, 1));
        nodesContact.add(new PointF(0, 10));
        nodesContact.add(new PointF(1, 2));
        nodesContact.add(new PointF(1, 11));
        nodesContact.add(new PointF(2, 3));
        nodesContact.add(new PointF(2, 12));
        nodesContact.add(new PointF(3, 4));
        nodesContact.add(new PointF(3, 13));
        nodesContact.add(new PointF(4, 5));
        nodesContact.add(new PointF(4, 14));
        nodesContact.add(new PointF(5, 6));
        nodesContact.add(new PointF(5, 15));
        nodesContact.add(new PointF(6, 7));
        nodesContact.add(new PointF(6, 16));
        nodesContact.add(new PointF(7, 8));
        nodesContact.add(new PointF(7, 17));
        nodesContact.add(new PointF(8, 9));
        nodesContact.add(new PointF(8, 18));
        nodesContact.add(new PointF(9, 19));
        nodesContact.add(new PointF(10, 11));
        nodesContact.add(new PointF(10, 21));
        nodesContact.add(new PointF(11, 12));
        nodesContact.add(new PointF(12, 13));
        nodesContact.add(new PointF(13, 14));
        nodesContact.add(new PointF(14, 15));
        nodesContact.add(new PointF(14, 20));
        nodesContact.add(new PointF(15, 16));
        nodesContact.add(new PointF(15, 20));
        nodesContact.add(new PointF(16, 17));
        nodesContact.add(new PointF(17, 18));
        nodesContact.add(new PointF(18, 19));
        nodesContact.add(new PointF(19, 23));
        nodesContact.add(new PointF(19, 64));
        nodesContact.add(new PointF(20, 22));
        nodesContact.add(new PointF(21, 22));
        nodesContact.add(new PointF(21, 24));
        nodesContact.add(new PointF(22, 25));
        nodesContact.add(new PointF(22, 23));
        nodesContact.add(new PointF(23, 26));
        nodesContact.add(new PointF(24, 25));
        nodesContact.add(new PointF(24, 27));
        nodesContact.add(new PointF(25, 26));
        nodesContact.add(new PointF(25, 28));
        nodesContact.add(new PointF(26, 29));
        nodesContact.add(new PointF(27, 28));
        nodesContact.add(new PointF(27, 30));
        nodesContact.add(new PointF(28, 29));
        nodesContact.add(new PointF(28, 31));
        nodesContact.add(new PointF(29, 32));
        nodesContact.add(new PointF(30, 31));
        nodesContact.add(new PointF(30, 33));
        nodesContact.add(new PointF(31, 32));
        nodesContact.add(new PointF(31, 34));
        nodesContact.add(new PointF(32, 35));
        nodesContact.add(new PointF(33, 34));
        nodesContact.add(new PointF(33, 36));
        nodesContact.add(new PointF(34, 35));
        nodesContact.add(new PointF(34, 37));
        nodesContact.add(new PointF(35, 38));
        nodesContact.add(new PointF(36, 37));
        nodesContact.add(new PointF(36, 39));
        nodesContact.add(new PointF(37, 38));
        nodesContact.add(new PointF(37, 40));
        nodesContact.add(new PointF(39, 40));
        nodesContact.add(new PointF(39, 42));
        nodesContact.add(new PointF(40, 41));
        nodesContact.add(new PointF(40, 43));
        nodesContact.add(new PointF(41, 44));
        nodesContact.add(new PointF(41, 65));
        nodesContact.add(new PointF(42, 43));
        nodesContact.add(new PointF(42, 45));
        nodesContact.add(new PointF(43, 44));
        nodesContact.add(new PointF(43, 46));
        nodesContact.add(new PointF(44, 47));
        nodesContact.add(new PointF(44, 65));
        nodesContact.add(new PointF(45, 48));
        nodesContact.add(new PointF(45, 59));
        nodesContact.add(new PointF(46, 60));
        nodesContact.add(new PointF(46, 61));
        nodesContact.add(new PointF(47, 61));
        nodesContact.add(new PointF(47, 62));
        nodesContact.add(new PointF(47, 65));
        nodesContact.add(new PointF(47, 58));
        nodesContact.add(new PointF(48, 49));
        nodesContact.add(new PointF(49, 50));
        nodesContact.add(new PointF(49, 59));
        nodesContact.add(new PointF(50, 55));
        nodesContact.add(new PointF(50, 51));
        nodesContact.add(new PointF(51, 52));
        nodesContact.add(new PointF(51, 56));
        nodesContact.add(new PointF(52, 53));
        nodesContact.add(new PointF(52, 57));
        nodesContact.add(new PointF(53, 54));
        nodesContact.add(new PointF(53, 58));
        nodesContact.add(new PointF(54, 62));
        nodesContact.add(new PointF(55, 56));
        nodesContact.add(new PointF(55, 60));
        nodesContact.add(new PointF(56, 57));
        nodesContact.add(new PointF(57, 61));
        nodesContact.add(new PointF(57, 58));
        nodesContact.add(new PointF(59, 60));
        nodesContact.add(new PointF(62, 71));
        nodesContact.add(new PointF(63, 67));
        nodesContact.add(new PointF(63, 72));
        nodesContact.add(new PointF(63, 64));
        nodesContact.add(new PointF(63, 66));
        nodesContact.add(new PointF(64, 65));
        nodesContact.add(new PointF(65, 66));
        nodesContact.add(new PointF(65, 71));
        nodesContact.add(new PointF(66, 68));
        nodesContact.add(new PointF(66, 70));
        nodesContact.add(new PointF(69, 70));

        return nodesContact;
    }

    public static List<PointF> getMarks() {
        List<PointF> marks = new ArrayList<>();
        marks.add(new PointF(850, 135));
        marks.add(new PointF(720, 135));
        marks.add(new PointF(610, 135));
        marks.add(new PointF(435, 135));
        marks.add(new PointF(270, 135));
        marks.add(new PointF(320, 255));
        marks.add(new PointF(530, 255));
        marks.add(new PointF(320, 355));
        marks.add(new PointF(320, 480));
        marks.add(new PointF(320, 605));
        marks.add(new PointF(530, 355));
        marks.add(new PointF(530, 430));
        marks.add(new PointF(530, 505));
        marks.add(new PointF(500, 610));
        marks.add(new PointF(220, 765));
        marks.add(new PointF(260, 765));
        marks.add(new PointF(300, 765));
        marks.add(new PointF(360, 710));
        marks.add(new PointF(475, 710));
        marks.add(new PointF(353, 780));
        marks.add(new PointF(430, 780));
        marks.add(new PointF(580, 765));
        marks.add(new PointF(645, 780));
        marks.add(new PointF(800, 685));
        marks.add(new PointF(900, 685));
        marks.add(new PointF(990, 685));
        marks.add(new PointF(1140, 685));
        marks.add(new PointF(1140, 455));
        marks.add(new PointF(785, 525));
        marks.add(new PointF(836, 315));
        marks.add(new PointF(1140, 343));
        marks.add(new PointF(1140, 260));
        marks.add(new PointF(970, 310));
        marks.add(new PointF(190, 280));
        marks.add(new PointF(190, 410));
        marks.add(new PointF(190, 480));
        marks.add(new PointF(190, 550));
        marks.add(new PointF(630, 195));
        marks.add(new PointF(630, 635));
        marks.add(new PointF(1020, 40));

        return marks;
    }

    public static List<String> getMarksName() {
        List<String> marksName = new ArrayList<>();
        for (int i = 0; i < getMarks().size(); i++) {
            marksName.add("Shop " + (i + 1));
        }
        return marksName;
    }
}
