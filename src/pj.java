import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by 13103 on 2016/10/14.
 * This file  is used to  play games.
 */


public class pj {

    public static void main(String[] args) throws FileNotFoundException {
        //读取tile文档中的字符串
        Scanner scanner1 = new Scanner(new File("tile.txt"));
        String firstLine = scanner1.nextLine();
        String secondLine = scanner1.nextLine();
        String thirdLine = scanner1.nextLine();
        String forLine = scanner1.nextLine();
        String fifLine = scanner1.nextLine();
        String sixLine = scanner1.nextLine();
        String sevenLine = scanner1.nextLine();
        String mapone[][] = new String[7][9];
        //给mapone2维数组赋值
        printtile1(mapone, firstLine, 0, 0);
        printtile1(mapone, secondLine, 1, 0);
        printtile1(mapone, thirdLine, 2, 0);
        printtile1(mapone, forLine, 3, 0);
        printtile1(mapone, fifLine, 4, 0);
        printtile1(mapone, sixLine, 5, 0);
        printtile1(mapone, sevenLine, 6, 0);
        //读取animal文档中的字符串
        Scanner scanner2 = new Scanner(new File("animal.txt"));
        String firstLine2 = scanner2.nextLine();
        String secondLine2 = scanner2.nextLine();
        String thirdLine2 = scanner2.nextLine();
        String forLine2 = scanner2.nextLine();
        String fifLine2 = scanner2.nextLine();
        String sixLine2 = scanner2.nextLine();
        String sevenLine2 = scanner2.nextLine();
        String maptwo[][] = new String[7][9];
        //给maptwo2维数组赋值
        printtile2(maptwo, firstLine2, 0, 0);
        printtile2(maptwo, secondLine2, 1, 0);
        printtile2(maptwo, thirdLine2, 2, 0);
        printtile2(maptwo, forLine2, 3, 0);
        printtile2(maptwo, fifLine2, 4, 0);
        printtile2(maptwo, sixLine2, 5, 0);
        printtile2(maptwo, sevenLine2, 6, 0);
        //定义三个变量，k为三维数组的层，i为行，j为列
        int k = 0;
        int i = 0;
        int j = 0;
        //把两个地图整合到一起
        while (i < 7) {
            while (j < 9) {
                if (maptwo[i][j].equals("1鼠 ") || maptwo[i][j].equals("2猫 ") || maptwo[i][j].equals("3狼 ") || maptwo[i][j].equals("4狗 ") ||
                        maptwo[i][j].equals("5豹 ") || maptwo[i][j].equals("6虎 ") || maptwo[i][j].equals("7狮 ") || maptwo[i][j].equals("8象 ")
                        || maptwo[i][j].equals(" 鼠1") || maptwo[i][j].equals(" 猫2") || maptwo[i][j].equals(" 狼3") || maptwo[i][j].equals(" 狗4") ||
                        maptwo[i][j].equals(" 豹5") || maptwo[i][j].equals(" 虎6") || maptwo[i][j].equals(" 狮7") || maptwo[i][j].equals(" 象8")) {
                    mapone[i][j] = maptwo[i][j];
                }
                j++;
            }
            i++;
            j = 0;
        }
        //定义一个三维数组
        String map[][][] = new String[10000][7][9];
        //把整合后的地图作为三维数组的第一层
        map[0] = mapone;
        //输出help内容
        help();
        //打印初始地图，即第1张地图
        systemout(map, k);
        //把当前的状态复制到下一张地图上，下次下棋就在下一张地图上改动
        mapchange(map[k], map[k + 1]);
        //控制玩家的变量
        boolean player = true;
        //控制可以悔棋步数的变量
        int redo = 0;
        //控制台
        Scanner input = new Scanner(System.in);
        String a = "";
        //第一层循环，知道输入exit才跳出
        stop1:
        while (true) {
            stop:
            //第2层循环，当有玩家胜利就跳出
            while (true) {
                int x = 0;
                i = 0;
                j = 0;
                //判断双方玩家是否没有了动物
                while (i < 7) {
                    while (j < 9) {
                        if (map[k][i][j].charAt(0) != ' ') {
                            x++;
                        }
                        j++;
                    }
                    i++;
                    j = 0;
                }
                if (x == 0) {
                    System.out.println("右方玩家获胜");
                    break stop;
                }
                x = 0;
                i = 0;
                j = 0;
                while (i < 7) {
                    while (j < 9) {
                        if (map[k][i][j].charAt(2) != ' ') {
                            x++;
                        }
                        j++;
                    }
                    i++;
                    j = 0;
                }
                if (x == 0) {
                    System.out.println("左方玩家获胜");
                    break stop;
                }
                //提示玩家应该哪一方行动
                whoplayermove(player);
                //从控制台输入一串字符
                a = input.nextLine();
                //判断输入的命令能否执行
                if (!readstring(player, a)) {
                    continue stop;
                }
                //读取字符串的第一个与第二个字符
                char input_oneChar = a.charAt(0);
                char input_twoChar = a.charAt(1);
                //输入help反应
                if (a.equals("help")) {
                    help();
                    continue stop;
                }
                //重新开始
                if (a.equals("restart")) {
                    systemout(map, 0);
                    k = 0;
                    mapchange(map[0], map[k + 1]);
                    player = true;
                    continue stop;
                }
                //悔棋
                if (a.equals("undo")) {

                    if (k - 1 < 0) {
                        System.out.println("这是初始地图不能再退悔棋了");
                        continue stop;
                    } else {
                        redo++;
                        k--;
                        systemout(map, k);
                        //mapchange(map[k], map[k + 1]);
                        player = !player;
                        continue stop;
                    }
                }
                //取消悔棋
                if (a.equals("redo")) {

                    if (redo - 1 < 0) {
                        System.out.println("你不能再取消悔棋了");
                        continue stop;
                    } else {
                        k = k + 1;
                        redo--;
                        systemout(map, k + 1);
                        player = !player;
                        continue stop;
                    }
                }
                //游戏结束
                if (a.equals("exit")) {
                    System.out.println("游戏结束");
                    break stop1;
                }
                mapchange(map[k], map[k + 1]);
                //左方玩家行动
                if (player) {

                    i = 0;
                    j = 0;
                    while (i < 7) {
                        while (j < 9) {
                            //读取一个字符串里的3个字符
                            char firstChar = map[k][i][j].charAt(0);
                            char secondChar = map[k][i][j].charAt(1);
                            char thirdChar = map[k][i][j].charAt(2);
                            //第一个字符与输入的第一个相等
                            if (firstChar == input_oneChar) {
                                //上下左右四种情况
                                switch (input_twoChar) {
                                    case 'w':
                                        //在上边缘，移动出界
                                        if (i == 0) {
                                            sout();
                                            continue stop;
                                        }
                                        //不能下水
                                        else if (!(secondChar == '鼠' | secondChar == '狮' | secondChar == '虎') & ((i == 3 | i == 6) & (j == 5 | j == 3 | j == 4))) {
                                            System.out.println(secondChar + "不能下水");
                                            continue stop;
                                        }
                                        //陷阱里没动物，进陷阱
                                        else if (map[k][i - 1][j].equals(" 陷 ")) {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //敌人在自己的陷阱，谁都能吃
                                        else if ((i == 4 & j == 1) || (i == 5 && j == 0)) {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //出陷阱，1.有自己的不能出，2。比自己大不能出。3其他出去
                                        else if ((i == 2 && (j == 0 || j == 8)) || (i == 3 && (j == 1 || j == 7))) {
                                            if (map[k][i - 1][j].charAt(0) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i - 1][j].charAt(2) != ' ') {
                                                if ((secondChar == '象' && map[k][i - 1][j].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                        map[k][i - 1][j].charAt(1) == '象') && firstChar < map[k][i - 1][j].charAt(2))) {
                                                    System.out.println(secondChar + "不能吃" + map[k][i - 1][j].charAt(1));
                                                    continue stop;
                                                } else {
                                                    map[k + 1][i - 1][j] = map[k][i][j];
                                                    map[k + 1][i][j] = " 陷 ";
                                                    k++;
                                                    systemout(map, k);
                                                    redo = 0;
                                                }
                                            } else {
                                                map[k + 1][i - 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 陷 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //出陷阱是家，或者胜利
                                        else if (i == 4 && (j == 0 || j == 8)) {
                                            if (j == 0) {
                                                System.out.println("不能走进自己的家");
                                                continue stop;
                                            } else {
                                                System.out.println("左方玩家获胜");
                                                break stop;
                                            }
                                        }
                                        //老鼠进水
                                        else if ((i == 3 || i == 6) && (j == 3 || j == 4 || j == 5) && secondChar == '鼠') {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠的水中移动
                                        else if ((i == 2 || i == 5) && (j == 3 || j == 4 || j == 5)) {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 水 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }   //鼠出水 ，岸上有除了鼠以外的动物出不来
                                        else if (((i == 1 || i == 4) && (j == 3 || j == 4 || j == 5) && secondChar == '鼠')) {
                                            if (map[k][i - 1][j].charAt(1) == '　' || map[k][i - 1][j].charAt(1) == '鼠') {
                                                map[k + 1][i - 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 水 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("不能从水里攻击陆地上的动物");
                                                continue stop;
                                            }
                                        } else if (((i == 6 || i == 3) && (j == 3 || j == 4 || j == 5) && (secondChar == '虎' || secondChar == '狮'))) {
                                            //水中有敌鼠不能过
                                            if (map[k][i - 1][j] == " 鼠1" || map[k][i - 2][j] == " 鼠1") {
                                                System.out.println("你这样走河里的老鼠会阿你");
                                                continue stop;
                                            } else if (map[k][i - 3][j].charAt(0) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            }
                                            //有敌方动物或者空地，能不能上岸
                                            else if (map[k][i - 3][j] == " 　 " || firstChar >= map[k][i - 3][j].charAt(2)) {
                                                map[k + 1][i - 3][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("打不过它");
                                                continue stop;
                                            }
                                        }
                                        //大吃小，象不吃鼠，鼠吃象
                                        else if (map[k][i - 1][j].charAt(2) != ' ') {
                                            if ((secondChar == '象' && map[k][i - 1][j].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                    map[k][i - 1][j].charAt(1) == '象') && firstChar < map[k][i - 1][j].charAt(2))) {
                                                System.out.println(secondChar + "不能吃" + map[k][i - 1][j].charAt(1));
                                                continue stop;
                                            } else {
                                                map[k + 1][i - 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //自己不能吃自己
                                        else if (map[k][i - 1][j].charAt(0) != ' ') {
                                            System.out.println("不能与友方单位重叠");
                                            continue stop;
                                        }
                                        //空白处的移动
                                        else {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        }
                                        i = 8;
                                        j = 9;
                                        break;

                                    case 's':
                                        //不能出界
                                        if (i == 6) {
                                            sout();
                                            continue stop;
                                        } //不能下水
                                        else if (!(secondChar == '鼠' | secondChar == '狮' | secondChar == '虎') & ((i == 0 | i == 3) & (j == 3 | j == 4 | j == 5))) {
                                            System.out.println(secondChar + "不能下水");
                                            continue stop;
                                        }
                                        //陷阱没动物，进陷阱
                                        else if (map[k][i + 1][j].equals(" 陷 ")) {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        }
                                        //敌人在自己的陷阱，谁都能吃
                                        else if ((i == 1 & j == 0) || (i == 2 && j == 1)) {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        }
                                        //出陷阱
                                        else if ((i == 4 && (j == 0 || j == 8)) || (i == 3 && (j == 1 || j == 7))) {
                                            if (map[k][i + 1][j].charAt(0) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i + 1][j].charAt(2) != ' ') {
                                                if ((secondChar == '象' && map[k][i + 1][j].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                        map[k][i + 1][j].charAt(1) == '象') && firstChar < map[k][i + 1][j].charAt(2))) {
                                                    System.out.println(secondChar + "不能吃" + map[k][i + 1][j].charAt(1));
                                                    continue stop;
                                                } else {
                                                    map[k + 1][i + 1][j] = map[k][i][j];
                                                    map[k + 1][i][j] = " 陷 ";
                                                    k++;
                                                    systemout(map, k);
                                                    redo = 0;
                                                }
                                            } else {
                                                map[k + 1][i + 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 陷 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        } else if (i == 2 && (j == 0 || j == 8)) {
                                            if (j == 0) {
                                                System.out.println("不能走进自己的家");
                                                continue stop;
                                            } else {
                                                System.out.println("左方玩家获胜");
                                                break stop;
                                            }
                                        }
                                        //鼠进水
                                        else if ((i == 0 || i == 3) && (j == 3 || j == 4 || j == 5) && secondChar == '鼠') {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        }
                                        //鼠的水中移动
                                        else if ((i == 1 || i == 4) && (j == 3 || j == 4 || j == 5)) {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 水 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        }
                                        //鼠出水 ，岸上有动物出不来
                                        else if (((i == 2 || i == 5) && (j == 3 || j == 4 || j == 5) && secondChar == '鼠')) {
                                            if (map[k][i + 1][j].charAt(1) == '　' || (map[k][i + 1][j].charAt(1) == '鼠')) {
                                                map[k + 1][i + 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 水 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            } else {
                                                System.out.println("不能从水里攻击陆地上的动物");
                                                continue stop;
                                            }
                                        } else if (((i == 0 || i == 3) && (j == 3 || j == 4 || j == 5) && (secondChar == '虎' || secondChar == '狮'))) {
                                            //水中有敌鼠不能过
                                            if (map[k][i + 1][j] == " 鼠1" || map[k][i + 2][j] == " 鼠1") {
                                                System.out.println("你这样走河里的老鼠会阿你");
                                                continue stop;
                                            } else if (map[k][i + 3][j].charAt(0) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i + 3][j] == " 　 " || firstChar >= map[k][i + 3][j].charAt(2)) {
                                                map[k + 1][i + 3][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            } else {
                                                System.out.println("打不过它");
                                                continue stop;
                                            }
                                        }  //大吃小，象不吃鼠，鼠吃象
                                        else if (map[k][i + 1][j].charAt(2) != ' ') {
                                            if ((secondChar == '象' && map[k][i + 1][j].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                    map[k][i + 1][j].charAt(1) == '象') && firstChar < map[k][i + 1][j].charAt(2))) {
                                                System.out.println(secondChar + "不能吃" + map[k][i + 1][j].charAt(1));
                                                continue stop;
                                            } else {
                                                map[k + 1][i + 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //自己不能吃自己
                                        else if (map[k][i + 1][j].charAt(0) != ' ') {
                                            System.out.println("不能与友方单位重叠");
                                            continue stop;
                                        } else {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        i = 8;
                                        j = 9;
                                        break;

                                    case 'a':
                                        //是否出界
                                        if (j == 0) {
                                            sout();
                                            continue stop;
                                        }
                                        //不能下水
                                        else if (!(secondChar == '鼠' | secondChar == '狮' | secondChar == '虎') & ((i == 1 | i == 2 | i == 4 | i == 5) & (j == 6))) {
                                            System.out.println(secondChar + "不能下水");
                                            continue stop;
                                        }
                                        //进陷阱
                                        else if (map[k][i][j - 1].equals(" 陷 ")) {
                                            map[k + 1][i][j - 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //敌人在自己的陷阱，谁都能吃
                                        else if ((i == 3 & j == 1) || (i == 4 && j == 0) || (i == 2 && j == 0)) {
                                            map[k + 1][i][j - 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //出陷阱出去了
                                        else if (((i == 4 || i == 2) && j == 8) || (i == 3 && (j == 7))) {
                                            if (map[k][i][j - 1].charAt(0) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i][j - 1].charAt(2) != ' ') {
                                                if ((secondChar == '象' && map[k][i][j - 1].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                        map[k][i][j - 1].charAt(1) == '象') && firstChar < map[k][i][j - 1].charAt(2))) {
                                                    System.out.println(secondChar + "不能吃" + map[k][i][j - 1].charAt(1));
                                                    continue stop;
                                                } else {
                                                    map[k + 1][i][j - 1] = map[k][i][j];
                                                    map[k + 1][i][j] = " 陷 ";
                                                    k++;
                                                    systemout(map, k);
                                                    redo = 0;
                                                }
                                            } else {
                                                map[k + 1][i][j - 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 陷 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //出陷阱没出去
                                        else if (i == 3 && j == 1) {
                                            System.out.println("不能走进自己的家");
                                            continue stop;
                                        }
                                        //鼠进水
                                        else if ((i == 1 || i == 2 | i == 4 | i == 5) && (j == 6) && secondChar == '鼠') {
                                            map[k + 1][i][j - 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠的水中移动
                                        else if ((i == 1 || i == 2 || i == 5 || i == 4) && (j == 4 || j == 5)) {
                                            map[k + 1][i][j - 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 水 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠出水 ，岸上有动物出不来
                                        else if (((i == 1 || i == 4 || i == 2 || i == 5) && (j == 3) && secondChar == '鼠')) {
                                            if (map[k][i][j - 1].charAt(1) == '　' || map[k][i][j - 1].charAt(1) == '鼠') {
                                                map[k + 1][i][j - 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 水 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("不能从水里攻击陆地上的动物");
                                                continue stop;
                                            }
                                        } else if (((i == 1 || i == 2 | i == 4 | i == 5) && (j == 6) && (secondChar == '虎' || secondChar == '狮'))) {
                                            //水中有敌鼠不能过
                                            if (map[k][i][j - 1] == " 鼠1" || map[k][i][j - 2] == " 鼠1" || map[k][i][j - 3] == " 鼠1") {
                                                System.out.println("你这样走河里的老鼠会阿你");
                                                continue stop;
                                            } else if (map[k][i][j - 4].charAt(0) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;

                                            } else if (map[k][i][j - 4] == " 　 " || firstChar >= map[k][i][j - 4].charAt(2)) {
                                                map[k + 1][i][j - 4] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("打不过它");
                                                continue stop;
                                            }
                                        } //大吃小，象不吃鼠，鼠吃象
                                        else if (map[k][i][j - 1].charAt(2) != ' ') {
                                            if ((secondChar == '象' && map[k][i][j - 1].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                    map[k][i][j - 1].charAt(1) == '象') && firstChar < map[k][i][j - 1].charAt(2))) {
                                                System.out.println(secondChar + "不能吃" + map[k][i][j - 1].charAt(1));
                                                continue stop;
                                            } else {
                                                map[k + 1][i][j - 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //自己不能吃自己
                                        else if (map[k][i][j - 1].charAt(0) != ' ') {
                                            System.out.println("不能与友方单位重叠");
                                            continue stop;
                                        }
                                        //空白处的行走
                                        else {
                                            map[k + 1][i][j - 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        i = 8;
                                        j = 9;
                                        break;


                                    case 'd':
                                        //不能出界
                                        if (j == 8) {
                                            sout();
                                            continue stop;
                                        }//不能下水
                                        else if (!(secondChar == '鼠' | secondChar == '狮' | secondChar == '虎') & ((i == 1 | i == 2 | i == 4 | i == 5) & (j == 2))) {
                                            System.out.println(secondChar + "不能下水");
                                            continue stop;
                                        }
                                        //进陷阱
                                        else if (map[k][i][j + 1].equals(" 陷 ")) {
                                            map[k + 1][i][j + 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //出陷阱出去了
                                        else if (((i == 4 || i == 2) && j == 0) || (i == 3 && (j == 1))) {
                                            if (map[k][i][j + 1].charAt(0) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i][j + 1].charAt(2) != ' ') {
                                                if ((secondChar == '象' && map[k][i][j + 1].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                        map[k][i][j + 1].charAt(1) == '象') && firstChar < map[k][i][j + 1].charAt(2))) {
                                                    System.out.println(secondChar + "不能吃" + map[k][i][j + 1].charAt(1));
                                                    continue stop;
                                                } else {
                                                    map[k + 1][i][j + 1] = map[k][i][j];
                                                    map[k + 1][i][j] = " 陷 ";
                                                    k++;
                                                    systemout(map, k);
                                                    redo = 0;
                                                }
                                            } else {
                                                map[k + 1][i][j + 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 陷 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //出陷阱赢了
                                        else if (i == 3 && j == 7) {

                                            System.out.println("左方玩家获胜");
                                            break stop;

                                        }
                                        //鼠进水
                                        else if ((i == 1 || i == 2 | i == 4 | i == 5) && (j == 2) && secondChar == '鼠') {
                                            map[k + 1][i][j + 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠的水中移动
                                        else if ((i == 2 || i == 5 || i == 1 || i == 4) && (j == 3 || j == 4)) {
                                            map[k + 1][i][j + 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 水 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠出水 ，岸上有动物出不来
                                        else if (((i == 1 || i == 4 || i == 2 || i == 5) && (j == 5) && secondChar == '鼠')) {
                                            if (map[k][i][j + 1].charAt(1) == '　' || map[k][i][j + 1].charAt(1) == '鼠') {
                                                map[k + 1][i][j + 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 水 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("不能从水里攻击陆地上的动物");
                                                continue stop;
                                            }
                                        } else if (((i == 1 || i == 2 | i == 4 | i == 5) && (j == 2) && (secondChar == '虎' || secondChar == '狮'))) {
                                            //水中有敌鼠不能过
                                            if (map[k][i][j + 1] == " 鼠1" || map[k][i][j + 2] == " 鼠1" || map[k][i][j + 3] == " 鼠1") {
                                                System.out.println("你这样走河里的老鼠会阿你");
                                                continue stop;
                                            } else if (map[k][i][j + 4].charAt(0) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i][j + 4] == " 　 " || firstChar >= map[k][i][j + 4].charAt(2)) {
                                                map[k + 1][i][j + 4] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("打不过它");
                                                continue stop;
                                            }
                                        }//大吃小，象不吃鼠，鼠吃象
                                        else if (map[k][i][j + 1].charAt(2) != ' ') {
                                            if ((secondChar == '象' && map[k][i][j + 1].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                    map[k][i][j + 1].charAt(1) == '象') && firstChar < map[k][i][j + 1].charAt(2))) {
                                                System.out.println(secondChar + "不能吃" + map[k][i][j + 1].charAt(1));
                                                continue stop;
                                            } else {
                                                map[k + 1][i][j + 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //自己不能吃自己
                                        else if (map[k][i][j + 1].charAt(0) != ' ') {
                                            System.out.println("不能与友方单位重叠");
                                            continue stop;
                                        }
                                        //空白处的交换
                                        else {

                                            map[k + 1][i][j + 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;

                                        }
                                        i = 8;
                                        j = 9;
                                        break;


                                }

                            }

                            j++;
                        }
                        i++;
                        j = 0;
                    }
                    //判断动物是否存在
                    if (exist(map, input_oneChar, k, 0) == -1) {
                        System.out.println("这个动物死了");
                        continue stop;
                    }
                    player = !player;
                } else {
                    i = 0;
                    j = 0;
                    while (i < 7) {
                        while (j < 9) {
                            char firstChar = map[k][i][j].charAt(0);
                            char secondChar = map[k][i][j].charAt(1);
                            char thirdChar = map[k][i][j].charAt(2);
                            if (thirdChar == input_oneChar) {
                                switch (input_twoChar) {
                                    case 'w':
                                        //是否出界
                                        if (i == 0) {
                                            sout();
                                            continue stop;
                                        }
                                        //不能下水
                                        else if (!(secondChar == '鼠' | secondChar == '狮' | secondChar == '虎') & ((i == 3 | i == 6) & (j == 5 | j == 3 | j == 4))) {
                                            System.out.println(secondChar + "不能下水");
                                            continue stop;
                                        }
                                        //进陷阱
                                        else if (map[k][i - 1][j].equals(" 陷 ")) {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //敌人在自己的陷阱，谁都能吃
                                        else if ((i == 4 & j == 7) || (i == 5 && j == 8)) {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //出陷阱
                                        else if ((i == 2 && (j == 0 || j == 8)) || (i == 3 && (j == 1 || j == 7))) {
                                            if (map[k][i - 1][j].charAt(2) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i - 1][j].charAt(0) != ' ') {
                                                if ((secondChar == '象' && map[k][i - 1][j].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                        map[k][i - 1][j].charAt(1) == '象') && thirdChar < map[k][i - 1][j].charAt(0))) {
                                                    System.out.println(secondChar + "不能吃" + map[k][i - 1][j].charAt(1));
                                                    continue stop;
                                                } else {
                                                    map[k + 1][i - 1][j] = map[k][i][j];
                                                    map[k + 1][i][j] = " 陷 ";
                                                    k++;
                                                    systemout(map, k);
                                                    redo = 0;
                                                }
                                            } else {
                                                map[k + 1][i - 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 陷 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        } else if (i == 4 && (j == 0 || j == 8)) {
                                            if (j == 8) {
                                                System.out.println("不能走进自己的家");
                                                continue stop;
                                            } else {
                                                System.out.println("右方玩家获胜");
                                                break stop;
                                            }
                                        }
                                        //鼠进水
                                        else if ((i == 3 || i == 6) && (j == 3 || j == 4 || j == 5) && secondChar == '鼠') {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠的水中移动
                                        else if ((i == 2 || i == 5) && (j == 3 || j == 4 || j == 5)) {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 水 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠出水 ，岸上有动物出不来
                                        else if (((i == 1 || i == 4) && (j == 3 || j == 4 || j == 5) && secondChar == '鼠')) {
                                            if (map[k][i - 1][j].charAt(1) == '　' || map[k][i - 1][j].charAt(1) == '鼠') {
                                                map[k + 1][i - 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 水 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("不能从水里攻击陆地上的动物");
                                                continue stop;
                                            }
                                        } else if (((i == 6 || i == 3) && (j == 3 || j == 4 || j == 5) && (secondChar == '虎' || secondChar == '狮'))) {
                                            //水中有敌鼠不能过
                                            if (map[k][i - 1][j] == "1鼠 " || map[k][i - 2][j] == "1鼠 ") {
                                                System.out.println("你这样走河里的老鼠会阿你");
                                                continue stop;
                                            } else if (map[k][i - 3][j].charAt(2) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;

                                            } else if (map[k][i - 3][j] == " 　 " || thirdChar >= map[k][i - 3][j].charAt(0)) {
                                                map[k + 1][i - 3][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("打不过它");
                                                continue stop;
                                            }
                                        }//大吃小，象不吃鼠，鼠吃象
                                        else if (map[k][i - 1][j].charAt(0) != ' ') {
                                            if ((secondChar == '象' && map[k][i - 1][j].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                    map[k][i - 1][j].charAt(1) == '象') && thirdChar < map[k][i - 1][j].charAt(0))) {
                                                System.out.println(secondChar + "不能吃" + map[k][i - 1][j].charAt(1));
                                                continue stop;
                                            } else {
                                                map[k + 1][i - 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //自己不能吃自己
                                        else if (map[k][i - 1][j].charAt(2) != ' ') {
                                            System.out.println("不能与友方单位重叠");
                                            continue stop;
                                        }
                                        //空白交换
                                        else {
                                            map[k + 1][i - 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        i = 8;
                                        j = 9;
                                        break;


                                    case 's':
                                        if (i == 6) {
                                            sout();
                                            continue stop;
                                        }
                                        //不能下水
                                        else if (!(secondChar == '鼠' | secondChar == '狮' | secondChar == '虎') & ((i == 0 | i == 3) & (j == 3 | j == 4 | j == 5))) {
                                            System.out.println(secondChar + "不能下水");
                                            continue stop;
                                        }
                                        //进陷阱
                                        else if (map[k][i + 1][j].equals(" 陷 ")) {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //敌人在自己的陷阱，谁都能吃
                                        else if ((i == 1 & j == 8) || (i == 2 && j == 7)) {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //出陷阱
                                        else if ((i == 4 && (j == 0 || j == 8)) || (i == 3 && (j == 1 || j == 7))) {
                                            if (map[k][i + 1][j].charAt(2) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i + 1][j].charAt(0) != ' ') {
                                                if ((secondChar == '象' && map[k][i + 1][j].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                        map[k][i + 1][j].charAt(1) == '象') && thirdChar < map[k][i + 1][j].charAt(0))) {
                                                    System.out.println(secondChar + "不能吃" + map[k][i + 1][j].charAt(1));
                                                    continue stop;
                                                } else {
                                                    map[k + 1][i + 1][j] = map[k][i][j];
                                                    map[k + 1][i][j] = " 陷 ";
                                                    k++;
                                                    systemout(map, k);
                                                    mapchange(map[k], map[k + 1]);
                                                    redo = 0;
                                                }
                                            } else {
                                                map[k + 1][i + 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 陷 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            }
                                        }
                                        //出陷阱没出去
                                        else if (i == 2 && (j == 0 || j == 8)) {
                                            if (j == 8) {
                                                System.out.println("不能走进自己的家");
                                                continue stop;
                                            }
                                            //出陷阱赢了
                                            else {
                                                System.out.println("右方玩家获胜");
                                                break stop;
                                            }
                                        }
                                        //鼠进水
                                        else if ((i == 0 || i == 3) && (j == 3 || j == 4 || j == 5) && secondChar == '鼠') {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠的水中移动
                                        else if ((i == 1 || i == 4) && (j == 3 || j == 4 || j == 5)) {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 水 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠出水 ，岸上有动物出不来
                                        else if (((i == 2 || i == 5) && (j == 3 || j == 4 || j == 5) && secondChar == '鼠')) {
                                            if (map[k][i + 1][j].charAt(1) == '　' || map[k][i + 1][j].charAt(1) == '鼠') {
                                                map[k + 1][i + 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 水 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            } else {
                                                System.out.println("不能从水里攻击陆地上的动物");
                                                continue stop;
                                            }
                                        } else if (((i == 0 || i == 3) && (j == 3 || j == 4 || j == 5) && (secondChar == '虎' || secondChar == '狮'))) {
                                            //水中有敌鼠不能过
                                            if (map[k][i + 1][j] == "1鼠 " || map[k][i + 2][j] == "1鼠 ") {
                                                System.out.println("你这样走河里的老鼠会阿你");
                                                continue stop;
                                            } else if (map[k][i + 3][j].charAt(2) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i + 3][j] == " 　 " || thirdChar >= map[k][i + 3][j].charAt(0)) {
                                                map[k + 1][i + 3][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            } else {
                                                System.out.println("打不过它");
                                                continue stop;
                                            }
                                        }
                                        //大吃小，象不吃鼠，鼠吃象
                                        else if (map[k][i + 1][j].charAt(0) != ' ') {
                                            if ((secondChar == '象' && map[k][i + 1][j].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                    map[k][i + 1][j].charAt(1) == '象') && thirdChar < map[k][i + 1][j].charAt(0))) {
                                                System.out.println(secondChar + "不能吃" + map[k][i + 1][j].charAt(1));
                                                continue stop;
                                            } else {
                                                map[k + 1][i + 1][j] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //自己不能吃自己
                                        else if (map[k][i + 1][j].charAt(2) != ' ') {
                                            System.out.println("不能与友方单位重叠");
                                            continue stop;
                                        } else {
                                            map[k + 1][i + 1][j] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        i = 8;
                                        j = 9;
                                        break;

                                    case 'a':
                                        //是否出界
                                        if (j == 0) {
                                            sout();
                                            continue stop;
                                        }
                                        //不能下水
                                        else if (!(secondChar == '鼠' | secondChar == '狮' | secondChar == '虎') & ((i == 1 | i == 2 | i == 4 | i == 5) & (j == 6))) {
                                            System.out.println(secondChar + "不能下水");
                                            continue stop;
                                        }
                                        //进陷阱
                                        else if (map[k][i][j - 1].equals(" 陷 ")) {
                                            map[k + 1][i][j - 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        }
                                        //出陷阱
                                        else if (((i == 4 || i == 2) && j == 8) || (i == 3 && (j == 7))) {
                                            if (map[k][i][j - 1].charAt(2) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i][j - 1].charAt(0) != ' ') {
                                                if ((secondChar == '象' && map[k][i][j - 1].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                        map[k][i][j - 1].charAt(1) == '象') && thirdChar < map[k][i][j - 1].charAt(0))) {
                                                    System.out.println(secondChar + "不能吃" + map[k][i][j - 1].charAt(1));
                                                    continue stop;
                                                } else {
                                                    map[k + 1][i][j - 1] = map[k][i][j];
                                                    map[k + 1][i][j] = " 陷 ";
                                                    k++;
                                                    systemout(map, k);
                                                    mapchange(map[k], map[k + 1]);
                                                    redo = 0;
                                                }
                                            } else {
                                                map[k + 1][i][j - 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 陷 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            }
                                        }
                                        //出陷阱赢了
                                        else if (i == 3 && j == 1) {
                                            System.out.println("右方玩家获胜");
                                            break stop;
                                        }
                                        //鼠进水
                                        else if ((i == 1 || i == 2 | i == 4 | i == 5) && (j == 6) && secondChar == '鼠') {
                                            map[k + 1][i][j - 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            map[k + 1] = map[k];
                                            redo = 0;
                                        }
                                        //鼠的水中移动
                                        else if ((i == 1 || i == 2 || i == 5 || i == 4) && (j == 4 || j == 5)) {
                                            map[k + 1][i][j - 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 水 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        //鼠出水 ，岸上有动物出不来
                                        else if (((i == 1 || i == 4 || i == 2 || i == 5) && (j == 3) && secondChar == '鼠')) {
                                            if (map[k][i][j - 1].charAt(1) == '　' || map[k][i][j - 1].charAt(1) == '鼠') {
                                                map[k + 1][i][j - 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 水 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("不能从水里攻击陆地上的动物");
                                                continue stop;
                                            }
                                        } else if (((i == 1 || i == 2 | i == 4 | i == 5) && (j == 6) && (secondChar == '虎' || secondChar == '狮'))) {
                                            //水中有敌鼠不能过
                                            if (map[k][i][j - 1] == "1鼠 " || map[k][i][j - 2] == "1鼠 " || map[k][i][j - 3] == "1鼠") {
                                                System.out.println("你这样走河里的老鼠会阿你");
                                                continue stop;
                                            } else if (map[k][i][j - 4].charAt(2) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;

                                            } else if (map[k][i][j - 4] == " 　 " || thirdChar >= map[k][i][j - 4].charAt(0)) {
                                                map[k + 1][i][j - 4] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            } else {
                                                System.out.println("打不过它");
                                                continue stop;
                                            }
                                        }   //大吃小，象不吃鼠，鼠吃象
                                        else if (map[k][i][j - 1].charAt(0) != ' ') {
                                            if ((secondChar == '象' && map[k][i][j - 1].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                    map[k][i][j - 1].charAt(1) == '象') && thirdChar < map[k][i][j - 1].charAt(0))) {
                                                System.out.println(secondChar + "不能吃" + map[k][i][j - 1].charAt(1));
                                                continue stop;
                                            } else {
                                                map[k + 1][i][j - 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                redo = 0;
                                            }
                                        }
                                        //自己不能吃自己
                                        else if (map[k][i][j - 1].charAt(2) != ' ') {
                                            System.out.println("不能与友方单位重叠");
                                            continue stop;
                                        } else {
                                            map[k + 1][i][j - 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            redo = 0;
                                        }
                                        i = 8;
                                        j = 9;
                                        break;


                                    case 'd':
                                        if (j == 8) {
                                            sout();
                                            continue stop;
                                        }   //不能下水
                                        else if (!(secondChar == '鼠' | secondChar == '狮' | secondChar == '虎') & ((i == 1 | i == 2 | i == 4 | i == 5) & (j == 2))) {
                                            System.out.println(secondChar + "不能下水");
                                            continue stop;
                                        }
                                        //进陷阱
                                        else if (map[k][i][j + 1].equals(" 陷 ")) {
                                            map[k + 1][i][j + 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        }
                                        //敌人在自己的陷阱，谁都能吃
                                        else if ((i == 3 & j == 6) || (i == 4 && j == 7) || (i == 2 && j == 7)) {
                                            map[k + 1][i][j + 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        } else if (i == 3 && j == 7) {
                                            System.out.println("不能走进自己的家");
                                            continue stop;
                                        }
                                        //出陷阱
                                        else if (((i == 4 || i == 2) && j == 0) || (i == 3 && (j == 1))) {
                                            if (map[k][i][j + 1].charAt(2) != ' ' || map[k][i][j + 1].charAt(2) != '鼠') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i][j + 1].charAt(0) != ' ') {
                                                if ((secondChar == '象' && map[k][i][j + 1].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                        map[k][i][j + 1].charAt(1) == '象') && thirdChar < map[k][i][j + 1].charAt(0))) {
                                                    System.out.println(secondChar + "不能吃" + map[k][i][j + 1].charAt(1));
                                                    continue stop;
                                                } else {
                                                    map[k + 1][i][j + 1] = map[k][i][j];
                                                    map[k + 1][i][j] = " 陷 ";
                                                    k++;
                                                    systemout(map, k);
                                                    mapchange(map[k], map[k + 1]);
                                                    redo = 0;
                                                }
                                            } else {
                                                map[k + 1][i][j + 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 陷 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            }
                                        }
                                        //鼠进水
                                        else if ((i == 1 || i == 2 | i == 4 | i == 5) && (j == 2) && secondChar == '鼠') {
                                            map[k + 1][i][j + 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        }
                                        //鼠的水中移动
                                        else if ((i == 2 || i == 5 || i == 1 || i == 4) && (j == 3 || j == 4)) {
                                            map[k + 1][i][j + 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 水 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;
                                        }
                                        //鼠出水 ，岸上有动物出不来
                                        else if (((i == 1 || i == 4 || i == 2 || i == 5) && (j == 5) && secondChar == '鼠')) {
                                            if (map[k][i][j + 1].charAt(1) == '　') {
                                                map[k + 1][i][j + 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 水 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            } else {
                                                System.out.println("不能从水里攻击陆地上的动物");
                                                continue stop;
                                            }
                                        }
                                        //大吃小，象不吃鼠，鼠吃象
                                        else if (((i == 1 || i == 2 | i == 4 | i == 5) && (j == 2) && (secondChar == '虎' || secondChar == '狮'))) {
                                            if (map[k][i][j + 1] == "1鼠 " || map[k][i][j + 2] == "1鼠 " || map[k][i][j + 3] == "1鼠 ") {
                                                System.out.println("你这样走河里的老鼠会阿你");
                                                continue stop;                             //水中有敌鼠不能过
                                            } else if (map[k][i][j + 4].charAt(2) != ' ') {
                                                System.out.println("不能与友方单位重叠");
                                                continue stop;
                                            } else if (map[k][i][j + 4] == " 　 " || thirdChar >= map[k][i][j + 4].charAt(0)) {
                                                map[k + 1][i][j + 4] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            } else {
                                                System.out.println("打不过它");
                                                continue stop;
                                            }
                                        } else if (map[k][i][j + 1].charAt(0) != ' ') {
                                            if ((secondChar == '象' && map[k][i][j + 1].charAt(1) == '鼠') || (!(secondChar == '鼠' &&
                                                    map[k][i][j + 1].charAt(1) == '象') && thirdChar < map[k][i][j + 1].charAt(0))) {
                                                System.out.println(secondChar + "不能吃" + map[k][i][j + 1].charAt(1));
                                                continue stop;
                                            } else {
                                                map[k + 1][i][j + 1] = map[k][i][j];
                                                map[k + 1][i][j] = " 　 ";
                                                k++;
                                                systemout(map, k);
                                                mapchange(map[k], map[k + 1]);
                                                redo = 0;
                                            }
                                        }
                                        //自己不能吃自己
                                        else if (map[k][i][j + 1].charAt(2) != ' ') {
                                            System.out.println("不能与友方单位重叠");
                                            continue stop;
                                        } else {

                                            map[k + 1][i][j + 1] = map[k][i][j];
                                            map[k + 1][i][j] = " 　 ";
                                            k++;
                                            systemout(map, k);
                                            mapchange(map[k], map[k + 1]);
                                            redo = 0;

                                        }
                                        i = 8;
                                        j = 9;
                                        break;


                                }

                            }

                            j++;
                        }
                        i++;
                        j = 0;
                    }
                    //判断动物是否死亡
                    if (exist(map, input_oneChar, k, 2) == -1) {
                        System.out.println("这个动物死了");
                        continue stop;
                    }
                    player = !player;
                }
            }

            if (!a.equals("restart")) {
                System.out.println("请输入restart重新开始游戏");
                a = input.nextLine();
                while (!a.equals("restart")) {
                    System.out.println("请输入restart重新开始游戏");
                    a = input.nextLine();
                }
                k = 0;
                i = 0;
                j = 0;
                player = true;
                systemout(map, k);
                continue stop1;
            }

        }
    }

    //这是打印地形的地图,看map每一行字符串a中的第row+1个元素是什么，一共看line+1行
    public static void printtile1(String map[][], String a, int line, int row) {
        while (row < 9) {
            char theChar = a.charAt(row);
            switch (theChar) {
                case '0':
                    map[line][row] = " 　 ";
                    break;
                case '1':
                    map[line][row] = " 水 ";
                    break;
                case '2':
                    map[line][row] = " 陷 ";
                    break;
                case '3':
                    map[line][row] = " 家 ";
                    break;
                case '4':
                    map[line][row] = " 陷 ";
                    break;
                case '5':
                    map[line][row] = " 家 ";
                    break;
            }
            row++;
        }


    }

    //这是打印动物的地图，同上
    public static void printtile2(String map[][], String a, int line, int row) {
        while (row < 9) {
            char theChar = a.charAt(row);
            switch (theChar) {
                case '0':
                    map[line][row] = " 　 ";
                    break;
                case '1':
                    map[line][row] = "1鼠 ";
                    break;
                case '2':
                    map[line][row] = "2猫 ";
                    break;
                case '3':
                    map[line][row] = "3狼 ";
                    break;
                case '4':
                    map[line][row] = "4狗 ";
                    break;
                case '5':
                    map[line][row] = "5豹 ";
                    break;
                case '6':
                    map[line][row] = "6虎 ";
                    break;
                case '7':
                    map[line][row] = "7狮 ";
                    break;
                case '8':
                    map[line][row] = "8象 ";
                    break;
                case '一':
                    map[line][row] = " 鼠1";
                    break;
                case '二':
                    map[line][row] = " 猫2";
                    break;
                case '三':
                    map[line][row] = " 狼3";
                    break;
                case '四':
                    map[line][row] = " 狗4";
                    break;
                case '五':
                    map[line][row] = " 豹5";
                    break;
                case '六':
                    map[line][row] = " 虎6";
                    break;
                case '七':
                    map[line][row] = " 狮7";
                    break;
                case '八':
                    map[line][row] = " 象8";
                    break;
            }
            row++;
        }
    }

    //打印第k张地图，String[][][] map是三维数组
    public static void systemout(String[][][] map, int k) {
        int row = 0;
        int line = 0;
        while (line < 7) {
            while (row < 9) {
                System.out.print(map[k][line][row]);
                row++;
            }
            line++;
            row = 0;
            System.out.println();
        }
    }

    //help指令
    public static void help() {
        System.out.println("指令介绍");
        System.out.println("   ");
        System.out.println("1.移动指令");
        System.out.println("    移动指令分为两部分");
        System.out.println("    第一部分是数字1-8，根据战斗力由小到大分别为1鼠，2猫，3狼，4狗，5豹，6虎，7狮，8象；需要注意的是老鼠可以杀死象");
        System.out.println("    第二部分是wasd中的一个，wasd分别对应上左下右");
        System.out.println("    例子，1w，表示鼠向上走");
        System.out.println("  ");
        System.out.println("2.游戏指令");
        System.out.println("    输入 restart 重新游戏");
        System.out.println("    输入 help 查看帮助");
        System.out.println("    输入 undo 悔棋");
        System.out.println("    输入 redo 取消悔棋");
        System.out.println("    输入 exit 退出游戏");
        //System.out.println("    输入 郑宇鑫真帅 你会看到更多小把戏");
        System.out.println();
    }

    //输出哪方玩家行动,player正确就是左方
    public static void whoplayermove(boolean player) {
        if (player) {
            System.out.print("左方玩家行动:");
        } else System.out.print("右方玩家行动:");
        return;
    }

    //判断输入的指令是否符合规定
    public static boolean readstring(boolean player, String a) {
        if (a.length() < 2) {
            System.out.println("不能识别指令" + "“" + a + "”" + "," + "请重新输入");
            return false;
        } else if (!(a.length() == 2) & !(a.equals("restart")) & (!(a.equals("help"))) & (!(a.equals("undo"))) & (!(a.equals("redo"))) & (!(a.equals("exit")))) {
            System.out.println("不能识别指令" + "“" + a + "”" + "," + "请重新输入");
            return false;
        } else {
            char onechar = a.charAt(0);
            char twochar = a.charAt(1);

            if (a.length() == 2) {
                if (!(onechar == '1' || onechar == '2' || onechar == '3' || onechar == '4' || onechar == '5' || onechar == '6' || onechar == '7' || onechar == '8')) {
                    System.out.println("不能识别指令" + "“" + a + "”" + "," + "请重新输入");
                    return false;
                }
                if (!(twochar == 'a' || twochar == 's' || twochar == 'd' || twochar == 'w')) {
                    System.out.println("不能识别指令" + "“" + a + "”" + "," + "请重新输入");
                    return false;
                }
            }
        }
        return true;
    }

    //如下
    public static void sout() {
        System.out.println("你不能这样走，它会走出地图");
    }

    //把第K张地图复印到第k+1张地图上
    public static void mapchange(String map1[][], String map2[][]) {
        int i = 0;
        int j = 0;
        while (i < 7) {
            while (j < 9) {
                map2[i][j] = map1[i][j];
                j++;
            }
            j = 0;
            i++;
        }
    }

    //判断该动物是否已经死亡，a是字符，看map[k][i][j]字符串里的第n个字符是否与a字符相等
    public static int exist(String map[][][], char a, int k, int n) {
        int i = 0;
        int j = 0;

        while (i < 7) {
            while (j < 9) {
                if (map[k][i][j].charAt(n) == a) {
                    return 1;
                }
                j++;
            }
            i++;
            j = 0;
        }
        return -1;
    }

}
