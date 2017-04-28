import java.util.*;
import java.io.*;

/**
 * Created by beej on 4/25/17.
 */
public class PasswordCrack {
    static List<String[]> fullName;
    static List<String[]> passCode;
    static List<List<String>> mainDict;
    static List<String> dict;
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
                                'R','S','T','U','V','W','X','Y','Z'};

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
                default:
                    break;
            }
            //tester();
        }

        public void start() {

        }
    }

    public static void main (String[] args) {
        // 0 - username  1 - first name   2 - last name
        PasswordCrack pObj = new PasswordCrack();


        fullName = new ArrayList<>();
        // 0 - salt    1 - hashcode
        passCode = new ArrayList<>();
        // file name of passwords
        String filename = "passwd2";
        dict = new ArrayList<>();
        jumbledNames = new ArrayList<>();
        // mapping
        map = new HashMap<>();
        mainDict = new ArrayList<>();
        foundNames = new ArrayList<>();
        threads = new ArrayList<>();
//        if (args.length != 2) {
//            System.out.println("Error: Incorrect number of arguments\nThe correct format is: java Encoder frequenciesFile k");
//            return;
//        } else {
//            filename = args[0];
//        }

        readPassFile(filename);
        startTime = System.currentTimeMillis();

        System.out.println("Building initial Dictionaries......");
        jumbleNames();
        readEtcFile("etc2");
        System.out.println("Built initial Dictionaries: " + mainDict.size());

        initialNameCheck();
        for (String key: foundNames)
            map.remove(key);
        foundNames = new ArrayList<>();
        //multithread

        //for (int i = 0; i < mainDict.size(); i++)
        System.out.println("Starting threads");
        pObj.createThread(0);
        System.out.println(map.size());
        pObj.createThread(1);
        System.out.println(map.size());
        pObj.createThread(2);

        //for (int i = 0; i < mainDict.size(); i++)
            //pObj.createThread2(i);

        //System.out.println("LLLL: " + foundNames.size());


//        initialDictCheck(mainDict.get(0));
//        System.out.println("Adding letters");
//
//        addLetters(mainDict.get(0));
//        System.out.println("Adding letter at the beg");
        //addLetterAtBeg();


        //System.out.println(jcrypt.crypt("<?", "mypass"));

        // read from file
        // store salt and hashed password
        // store first and last name


    }

    public void createThread(int mangle) {
        //initialDict nObj = new initialDict();
        mainThread nObj = new mainThread();
        //nObj.ID = ID;
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
//        if (ID == mainDict.size() -  1) {
//            try{
//                t.join();
//                for (String key: foundNames)
//                    map.remove(key);
//                foundNames = new ArrayList<>();
//            } catch (InterruptedException e) {
//
//            }
//        }
    }


    public static void tester () {
        System.out.println("IN TESTER");
    }





    public static void initialNameCheck() {
        //HashMap<String, List<String>> map2 = new HashMap<>(map);
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
            //map.keySet().removeIf(e -> check(map.get(e).get(3), dictWord, e));

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

    public static boolean check(String pre, String word, String hash) {
        if (jcrypt.crypt(pre, word).compareTo(hash) == 0) {
            foundPass2(hash, word);
            return true;
        }
        return false;
    }


    public static void addLettersEnd(List<String> dictionary) {
        List<String> keys = new ArrayList<>(map.keySet());
        for (String dictWord: dictionary) {
            if (dictWord.length() < 8) {
                for (char letter : addLetters) {
                    //map.keySet().removeIf(e -> check(map.get(e).get(3), letter + dictWord, e) || check(map.get(e).get(3), dictWord + letter, e));
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
                    //map.keySet().removeIf(e -> check(map.get(e).get(3), letter + dictWord, e) || check(map.get(e).get(3), dictWord + letter, e));
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

    public static void foundPass2(String key, String dictWord) {
        foundNum++;
        endTime = (System.currentTimeMillis() - startTime)/1000;
        System.out.printf("Found %d/20\n", foundNum);
        System.out.printf("Found password for %s, the password was '%s', found in %d seconds\n", map.get(key).get(1) + " " + map.get(key).get(2), dictWord, endTime);

    }

    public static Set<String> permute(String chars)
    {
        // Use sets to eliminate semantic duplicates (aab is still aab even if you switch the two 'a's)
        // Switch to HashSet for better performance
        Set<String> set = new TreeSet<String>();

        // Termination condition: only 1 permutation for a string of length 1
        if (chars.length() == 1)
        {
            set.add(chars);
        }
        else
        {
            // Give each character a chance to be the first in the permuted string
            for (int i=0; i<chars.length(); i++)
            {
                // Remove the character at index i from the string
                String pre = chars.substring(0, i);
                String post = chars.substring(i+1);
                String remaining = pre+post;

                // Recurse to find all the permutations of the remaining chars
                for (String permutation : permute(remaining))
                {
                    // Concatenate the first character with the permutations of the remaining chars
                    String newStr = chars.charAt(i) + permutation;
                    // add different lengths
                    set.add(newStr);

                }
            }
        }
        return set;
    }


    static void combine(String instr, StringBuffer outstr, int index)
    {
        for (int i = index; i < instr.length(); i++)
        {
            outstr.append(instr.charAt(i));
            System.out.println(outstr);
            combine(instr, outstr, i + 1);
            outstr.deleteCharAt(outstr.length() - 1);
        }
    }


    private static void generateAll(int k, char[] set, char[] str, int index) {
        if (index == k)
            System.out.println(new String(str));
        else {
            for (int i = 0; i < set.length; i++){
                str[index] = set[i];
                generateAll(k, set, str, index + 1);
            }
        }
    }

    public static void permutation(String str) {
        permutation("", str);
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
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
//        for (int i = 0; i < fullName.size(); i++) {
//            List<String> newNames = new ArrayList<>();
//            String[] names = fullName.get(i);
//            //regular names add
//            newNames.add(names[0]);
//            newNames.add(names[1]);
//            newNames.add(names[2]);
//
//            newNames.add(names[1] + names[2]);
//            newNames.add(names[2] + names[1]);
//            newNames.add(names[0] + names[2]);
//            // add reverse
//            newNames.add(new StringBuffer(names[0]).reverse().toString());
//            newNames.add(new StringBuffer(names[1]).reverse().toString());
//            newNames.add(new StringBuffer(names[2]).reverse().toString());
//
//            newNames.add(new StringBuffer(names[0]).reverse().toString() + names[0]);
//            newNames.add(new StringBuffer(names[1]).reverse().toString() + names[1]);
//            newNames.add(new StringBuffer(names[2]).reverse().toString() + names[2]);
//
//            newNames.add(names[0] + new StringBuffer(names[0]).reverse().toString());
//            newNames.add(names[1] + new StringBuffer(names[1]).reverse().toString());
//            newNames.add(names[2] + new StringBuffer(names[2]).reverse().toString());
//
//            jumbledNames.add(newNames);
//        }


    }


    private static void readPassFile (String filename) {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                //List<List<String>> data = new ArrayList<>();
                List<String> linedata = new ArrayList<>();
                String[] hash = new String[2];
                String[] perLine = line.split(":");
                linedata.add(perLine[0]);
                linedata.add(perLine[4].split(" ")[0]);
                linedata.add(perLine[4].split(" ")[1]);
                linedata.add(perLine[1].substring(0, 2));
                //data.add(linedata);
                map.put(perLine[1], linedata);
                String[] names = {perLine[0], perLine[4].split(" ")[0], perLine[4].split(" ")[1]};
                fullName.add(names);
                hash[0] = perLine[1].substring(0, 2);
                hash[1] = perLine[1];
                passCode.add(hash);

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

    private static void writeEtcFile() {
        try {
            PrintWriter fw = new PrintWriter(new FileWriter("finaletc"));
            for (String str : dict) {
                fw.println(str);
            }

        } catch (Exception io) {

        }


    }
}
