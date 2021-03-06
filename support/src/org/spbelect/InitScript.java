package org.spbelect;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitScript {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("support/all.csv")), "UTF-8"));
        in.readLine();
        String s;
        Map<Integer, Uik> uiks = new HashMap<Integer, Uik>();
        while ((s = in.readLine()) != null) {
            String[] d = s.split(",");
            //1850,2,165,Василеостровский,Самойлов Сергей Павлович,1969,высшее профессиональное,ЯБЛОКО,нет,,,,,,
           //System.out.println(s);
            int uikId = Integer.parseInt(d[2]);
            if (!uiks.containsKey(uikId)) {
                uiks.put(uikId, new Uik(uikId, Integer.parseInt(d[1])));
            }
            Uik u = uiks.get(uikId);
            u.persons.add(new Person(d[4], Integer.parseInt(d[5]), getSource(d[7])));
        }

        for (Uik u : uiks.values()) {
            File dir  = new File("tik" + u.tik);
            dir.mkdirs();
            File file = new File(dir, "uik" + u.uik + ".md");
            file.createNewFile();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            //System.out;


            out.println("#Состав");

            for (int i = 0; i < u.persons.size(); i++) {
                Person p =  u.persons.get(i);
                out.println((i + 1) + ". " + p.name + " " + p.year + "   " );
                out.println("    " + p.source);
            }
            out.close();
        }
    }

    static String getSource(String s) {
        s = s.replaceAll("\"\"", "\"");
        s = s.replaceAll("«", "\"");
        s = s.replaceAll("»", "\"");
        s = s.replaceAll("\"\"", "\"");
        s = s.replaceAll("\\\"", "\"");
        String[] keys = new String[] {"Российский Красный Крест", "Чернобыль-Нева", "ГАООРДИ", "ОРТОЛЮКС", "За женщин России",
            "Российский Союз Молодежи", "За женщин России", "КОММУНИСТЫ РОССИИ", "ЯБЛОКО", "Жители блокадного Ленинграда", "ПАРТИЯ ЗА СПРАВЕДЛИВОСТЬ", "Апрель",
            "Партия пенсионеров России", "МО Константиновское", "МО Горелово", "МО город Красное Село", "МО Пулковский Меридиан",
            "МО Смольнинское", "МО Георгиевский", "МО Балканский", "МО Коломяги", "МО Юнтолово", "МО Комендантский аэродром", "МО Новоизмайловское", "МО Нарвский округ"
        };
        for (String key : keys) {
            if (s.toLowerCase().indexOf(key.toLowerCase()) > -1) {
                return key;
            }
        }
        switch (s) {
            case "Политическая партия \"КОММУНИСТИЧЕСКАЯ ПАРТИЯ РОССИЙСКОЙ ФЕДЕРАЦИИ\"":
            case "Политическая партия \"коммунистами СКАЯ ПАРТИЯ РОССИЙСКОЙ ФЕДЕРАЦИИ\"":
            case "Политическая партия \"КОММУНИСТИЧЕСКАЯ ПАРТИЯ РОССИЙСКОЙ ФЕДЕРАЦИИ":
            case "Василеостровское местное(районное) отделение Санкт-Петербургского городского отделения политической партии \"Коммунистическая партия Российской Федерации\"":
            case "КПРФ":
                return "КПРФ";

            case "Политическая партия СПРАВЕДЛИВАЯ РОССИ":
            case "СР":
                return s;
            case "Всероссийская политическая партия \"ЕДИНАЯ РОССИЯ\"":
            case "ЕДРО":
                return "ЕР";

            case "Политическая партия \"Либерально-демократическая партия России\"":
            case "ЛДПР":
                return s;

            case "Политическая партия \"Гражданская Платформа\"":
                return "Гражданская Платформа";

            case "Политическая партия \"Российская партия пенсионеров за справедливость\"":
                return "Российская партия пенсионеров за справедливость";
            case "Политическая партия \"ПАТРИОТЫ РОССИИ\"":
                return "ПАТРИОТЫ РОССИИ";
            case "Корпус наблюдателей \"За чистые выборы\" ":
                return "За чистые выборы";
            case "Политическая партия \"Трудовая партия России\"":
                return "Трудовая партия России";
            case "Политическая партия СОЦИАЛЬНОЙ ЗАЩИТЫ": return "СОЦИАЛЬНОЙ ЗАЩИТЫ";

            case "собрание избирателей по месту раооты - администрация Адмиралтейского района Санкт-Петербурга":
            case "собрание избирателей по месту работы - администрация Адмиралтейского района Санкт-Петербурга":
            case "собрание избирателей по месту работы - администрация Адмиралтейского района":
                return "собрание Адмиралтейского";

            case "\"Политическая партия \"Трудовая партия России\"":
                return "Трудовая партия России";
            case "Всероссийская политическая партия \"ПРАВОЕ ДЕЛО\"":
                return "ПРАВОЕ ДЕЛО";

            case "собрание по месту работы":
            case "собрание избирателей по месту работы":
                return "собрание-работа";

            case "собрание избирателей по месту жительства":
                return "собрание-дом";

            case "\"ФОНД \"Будущее Родины\"":
                return "Будущее Родины";

            case "Межрегиональное общественное объединение \"Союз женщин Санкт-Петербурга и Ленинградской области\" Союза женщин России\"":
            case "\"Межрегиональная общественная организация \"Союз женщин Санкт-Петербурга и Ленинградской области\" Союза женщин России\"":
            case "\"Межрегиональное общественное объединение \"Союз женщин Санкт-Петербурга и Ленинградской области\" Союза женщин России\"":
            case "\"Общероссийская общественная организация \"Союз женщин России\"":
                return "Союз женщин России";

            case "собрание избирателей по месту работы - ГБОУ №700": return "собрание ГБОУ 700";

            case "собрание избирателей по месту работы - ГБОУ гимназия №642":
                return "собрание ГБОУ 642";

            case "собрание избирателей по месту учебы":
                return "собрание-учеба";

            case "\"Санкт-Петербургская региональная общественная организация инвалидов за независимую жизнь \"Мы-вместе\"":
            case "\"СПбРОО \"Мы- вместе\"":
                return "Мы-вместе";

            case "\"Политическая партия \"ПАТРИОТЫ РОССИИ\"":
                return "ПАТРИОТЫ РОССИИ";

            case "\"Общероссийская общественная организация \"Российский Союз Молодежи\"":
                return "Российский Союз Молодежи";


            case "Общероссийская общественная организация Всероссийское общество инвалидов (БОИ)":
            case "Общероссийская общественная организация Всероссийское общество инвалидов (ВОИ)":
                return "ВОИ";
            case "СОО бывших малолетних узников фашистских концлагерей г.Санкт-Петербург":
                return "узники концлагерей";


            case "\"МС МО \"Балканский\"":
                return "МО Балканский";

            case "\"МС МО \"Георгиевский\"":
                return "МО Георгиевский";


        }

        String s2 = s.replaceAll("\"", "\\\\\"");
        System.out.println("case \"" + s2 + "\": \n return \"\";");
        return s;
    }

    static class Uik {
        List<Person> persons = new ArrayList<Person>();
        int uik;
        int tik;

        Uik(int uik, int tik) {
            this.uik = uik;
            this.tik = tik;
        }
    }

    static class Person {
        String name;
        int year;
        String  source;

        Person(String name, int year, String source) {
            this.name = name;
            this.year = year;
            this.source = source;
        }
    }
}
