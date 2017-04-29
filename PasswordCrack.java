import java.util.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by beej on 4/25/17.
 */
public class PasswordCrack {
    static List<List<String>> mainDict;
    static List<List<String>> jumbledNames;
    static HashMap<String, List<String>> map;
    static List<String> foundNames;
    static List<Thread> threads;
    static long startTime;
    static long endTime;
    static int foundNum = 0;
    static char[] addLetters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o',
                                'p','q','r','s','t','u','v','w','x','y','z','1','2','3','4',
                                '5','6','7','8','9','0','!','@','$','&','^','*','%','`','?','#',
                                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
                                'R','S','T','U','V','W','X','Y','Z','(',')','|',',','.'};

    public class mainThread extends Thread {
        private int mangle;
        public void run() {
            for (int i = 0; i < mainDict.size(); i++) {
                initialDict nObj = new initialDict();
                //mainThread nObj = new mainThread();
                nObj.ID = i;
                nObj.mangle = mangle;
                Thread t = new Thread(nObj);
                threads.add(t);
                t.start();
            }
        }

        public void start() {

        }
    }

    public class initialDict extends Thread {
        private int mangle;
        private int ID;
        public void run() {
            //System.out.println("Length: " + mainDict.size() + " " + ID);
            switch (mangle) {
                case 0:
                    initialDictCheck(mainDict.get(ID));
                    break;
                case 1:
                    addLettersEnd(mainDict.get(ID));
                    break;
                case 2:
                    addLetterBeg(mainDict.get(ID));
                    break;
                case 3:
                    randomLetters();
                    break;
                default:
                    break;
            }
        }
    }

    public static void main (String[] args) {
        // 0 - username  1 - first name   2 - last name
        PasswordCrack pObj = new PasswordCrack();
        // file name of passwords
        String filename = "passwd2";
        jumbledNames = new ArrayList<>();
        // mapping
        map = new HashMap<>();
        mainDict = new ArrayList<>();
        foundNames = new ArrayList<>();
        threads = new ArrayList<>();

        if (args.length != 2) {
            System.out.println("Error: Incorrect number of arguments\nThe correct format is: java PasswordCrack inputFile1 inputFile2");
            return;
        } else {
            filename = args[1];
        }
        readPassFile(filename);
        startTime = System.currentTimeMillis();

        System.out.println("Building initial Dictionaries......");
        jumbleNames();
        readEtcFile(args[0]);
        System.out.println("Built initial Dictionaries: " + mainDict.size());

        initialNameCheck();
        for (String key: foundNames)
            map.remove(key);
        foundNames = new ArrayList<>();
        //multithread

        //for (int i = 0; i < mainDict.size(); i++)
        System.out.println("Starting threads");
        pObj.createThread(0);
        //System.out.println(map.size());
        pObj.createThread(1);
        //System.out.println(map.size());
        pObj.createThread(2);
        System.out.println("Attempting random letters");
        pObj.createThread(3);


    }

    public void createThread(int mangle) {
        mainThread nObj = new mainThread();
        nObj.mangle = mangle;
        Thread t = new Thread(nObj);
        t.start();

        try {
            t.join();
            for (Thread thread: threads)
                thread.join();

            for (String key: foundNames)
                map.remove(key);
            foundNames = new ArrayList<>();
        } catch (InterruptedException e) {

        }
    }



    public static void initialNameCheck() {
        List<String> keys = new ArrayList<>(map.keySet());
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            for (String checkName: map.get(key)) {
                if (jcrypt.crypt(map.get(key).get(3), checkName).compareTo(key) == 0) {
                    foundPass(key, checkName);
                    keys.remove(i);
                    i--;
                    break;

                }
            }
        }

    }

    public static void initialDictCheck(List<String> dictionary) {
        List<String> keys = new ArrayList<>(map.keySet());
        for (String dictWord: dictionary) {
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                if (jcrypt.crypt(map.get(key).get(3), dictWord).compareTo(key) == 0) {
                    foundPass(key, dictWord);
                    keys.remove(i);
                    i--;
                    break;
                }
            }
        }

    }

    public static void randomLetters() {
        List<String> keys = new ArrayList<>(map.keySet());
        while (true) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                str.append(addLetters[ThreadLocalRandom.current().nextInt(0, addLetters.length)]);
            }

            for (String key: keys) {
                if (jcrypt.crypt(map.get(key).get(3), str.toString()).compareTo(key) == 0) {
                    foundPass(key, str.toString());
                    break;
                }
            }
            if (foundNum == 20)
                return;
        }
    }

    public static void addLettersEnd(List<String> dictionary) {
        List<String> keys = new ArrayList<>(map.keySet());
        for (String dictWord: dictionary) {
            if (dictWord.length() < 8) {
                for (char letter : addLetters) {
                    for (int i = 0; i < keys.size(); i++) {
                        String key = keys.get(i);
                        if (jcrypt.crypt(map.get(key).get(3), dictWord + letter).compareTo(key) == 0) {
                            foundPass(key, dictWord + letter);
                            keys.remove(i);
                            i--;
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void addLetterBeg(List<String> dictionary) {
        List<String> keys = new ArrayList<>(map.keySet());
        for (String dictWord: dictionary) {
            if (dictWord.length() < 8) {
                for (char letter : addLetters) {
                    for (int i = 0; i < keys.size(); i++) {
                        String key = keys.get(i);
                        if (jcrypt.crypt(map.get(key).get(3), letter + dictWord).compareTo(key) == 0) {
                            foundPass(key, letter + dictWord);
                            keys.remove(i);
                            i--;
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void foundPass(String key, String dictWord) {
        foundNum++;
        endTime = (System.currentTimeMillis() - startTime)/1000;
        System.out.printf("Found %d/20\n", foundNum);
        System.out.printf("Found password for %s, the password was '%s', found in %d seconds\n", map.get(key).get(1) + " " + map.get(key).get(2), dictWord, endTime);
        //map.remove(key);
        foundNames.add(key);

    }

    private static void jumbleNames () {
        for (String key: map.keySet()) {
            List<String> data = map.get(key);
            String username = data.get(0);
            String firstname = data.get(1);
            String lastname = data.get(2);


            data.add(firstname + lastname);
            data.add(lastname + firstname);
            data.add(username + lastname);
            // add reverse
            data.add(new StringBuffer(username).reverse().toString());
            data.add(new StringBuffer(firstname).reverse().toString());
            data.add(new StringBuffer(lastname).reverse().toString());

            data.add(new StringBuffer(username).reverse().toString() + username);
            data.add(new StringBuffer(firstname).reverse().toString() + firstname);
            data.add(new StringBuffer(lastname).reverse().toString() + lastname);

            data.add(username + new StringBuffer(username).reverse().toString());
            data.add(firstname + new StringBuffer(firstname).reverse().toString());
            data.add(lastname + new StringBuffer(lastname).reverse().toString());

            map.put(key, data);

        }

    }


    private static void readPassFile (String filename) {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<String> linedata = new ArrayList<>();
                String[] perLine = line.split(":");
                linedata.add(perLine[0]);
                linedata.add(perLine[4].split(" ")[0]);
                linedata.add(perLine[4].split(" ")[1]);
                linedata.add(perLine[1].substring(0, 2));
                map.put(perLine[1], linedata);

            }
        } catch (Exception io) {
            System.out.println(io);
            // except
        }
    }

    private static void readEtcFile (String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            HashSet<String> data = new HashSet<>();
            int count = 0;
            while ((line = br.readLine()) != null) {
                String reversed;
                if (line.length() > 8) {
                    reversed = new StringBuffer(line).reverse().toString().substring(0,8);
                    data.add(reversed);
                    line = line.substring(0, 8);
                    data.add(line);
                    data.add(line.toUpperCase());
                } else {
                    data.add(line);
                    data.add(line.toUpperCase());
                    reversed = new StringBuffer(line).reverse().toString();
                    data.add(reversed);
                }
                String _first = line.substring(0, line.length() - 1);
                String _last = line.substring(1, line.length());
                data.add(_first);
                data.add(_last);

                if (line.length() > 2 && line.length() < 9) {
                    data.add(_first.substring(0, 1) + _first.substring(1, _first.length()).toUpperCase());
                    data.add(_first.substring(0, 1).toUpperCase() + _first.substring(1, _first.length()));
                    data.add(new StringBuffer(_first.substring(0, 1).toUpperCase() + _first.substring(1, _first.length())).reverse().toString());

                    data.add(_last.substring(0, 1) + _last.substring(1, _last.length()).toUpperCase());
                    data.add(_last.substring(0, 1).toUpperCase() + _last.substring(1, _last.length()));
                    data.add(new StringBuffer(_last.substring(0, 1).toUpperCase() + _last.substring(1, _last.length())).reverse().toString());

                    data.add(line.substring(0, 1) + line.substring(1, line.length()).toUpperCase());
                    data.add(line.substring(0, 1).toUpperCase() + line.substring(1, line.length()));
                    data.add(new StringBuffer(line.substring(0, 1).toUpperCase() + line.substring(1, line.length())).reverse().toString());

                }

                if (line.length() < 8) {
                    data.add(line + line);
                    data.add(line + reversed);
                    data.add(reversed + line);
                }

                //Alternating caps

                StringBuilder str1 = new StringBuilder();
                StringBuilder str2 = new StringBuilder();
                int count2 = 0;
                for (char c : line.toCharArray()) {
                    if (count % 2 == 0) {
                        str1.append(Character.toString(c).toUpperCase());
                        str2.append(c);
                    } else {
                        str2.append(Character.toString(c).toUpperCase());
                        str1.append(c);
                    }
                    count2++;
                }
                data.add(str1.toString());
                data.add(str2.toString());


            }

            List<String> newData = new ArrayList<>();
            for (String word: data) {
                newData.add(word);
                if (newData.size() > 30000) {
                    mainDict.add(newData);
                    newData = new ArrayList<>();
                }

            }
            if (newData.size() > 0) {
                mainDict.add(newData);
            }
        } catch (Exception io) {
            System.out.println(io);
            // except
        }
    }

}
