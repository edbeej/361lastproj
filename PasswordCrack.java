import java.util.*;
import java.io.*;

/**
 * Created by beej on 4/25/17.
 */
public class PasswordCrack {
    static List<String[]> fullName;
    static List<String[]> passCode;
    static List<String> dict;
    static List<List<String>> jumbledNames;
    static HashMap<String, List<String>> map;

    static long startTime;
    static long endTime;
    static int foundNum = 0;
    static char[] addLetters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o',
                                'p','q','r','s','t','u','v','w','x','y','z','1','2','3','4',
                                '5','6','7','8','9','0','!','@','$','&','^','*'};

    public static void main (String[] args) {
        // 0 - username  1 - first name   2 - last name

        fullName = new ArrayList<>();
        // 0 - salt    1 - hashcode
        passCode = new ArrayList<>();
        // file name of passwords
        String filename = "src/passwd1";
        dict = new ArrayList<>();
        jumbledNames = new ArrayList<>();
        // mapping
        map = new HashMap<>();

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
        readEtcFile("src/etc2");
        System.out.println("Built initial Dicitonaries");

        initialNameCheck();
        initialDictCheck();
        System.out.println("Adding letters");

        addLetters();
        System.out.println("Adding letter at the beg");
        //addLetterAtBeg();

        //System.out.println(jcrypt.crypt("<?", "mypass"));

        // read from file
        // store salt and hashed password
        // store first and last name


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

    public static void initialDictCheck() {
        //List<String> keys = new ArrayList<>(map.keySet());
        for (String dictWord: dict) {
            map.keySet().removeIf(e -> check(map.get(e).get(3), dictWord, e));

//            for (int i = 0; i < keys.size(); i++) {
//                String key = keys.get(i);
//                if (jcrypt.crypt(map.get(key).get(3), dictWord).compareTo(key) == 0) {
//                    foundPass(key, dictWord);
//                    keys.remove(i);
//                    i--;
//                    break;
//                }
//            }
        }

    }

    public static boolean check(String pre, String word, String hash) {
        if (jcrypt.crypt(pre, word).compareTo(hash) == 0) {
            foundPass2(hash, word);
            return true;
        }
        return false;
    }


    public static void addLetters() {
        //List<String> keys = new ArrayList<>(map.keySet());
        for (String dictWord: dict) {
            if (dictWord.length() < 8) {
                for (char letter : addLetters) {
                    map.keySet().removeIf(e -> check(map.get(e).get(3), letter + dictWord, e) || check(map.get(e).get(3), dictWord + letter, e));
//                    for (int i = 0; i < keys.size(); i++) {
//                        String key = keys.get(i);
//                        if (jcrypt.crypt(map.get(key).get(3), dictWord + letter).compareTo(key) == 0) {
//                            foundPass(key, dictWord + letter);
//                            keys.remove(i);
//                            i--;
//                            break;
//                        } else if (jcrypt.crypt(map.get(key).get(3), letter + dictWord).compareTo(key) == 0) {
//                            foundPass(key, letter + dictWord);
//                            keys.remove(i);
//                            i--;
//                            break;
//                        }
//                    }
                }
            }
        }
    }

    public static void addLetterAtBeg() {

        for (String dictWord: dict) {
            if (dictWord.length() < 8) {
                for (char letter : addLetters) {
                    for (int i = 0; i < passCode.size(); i++) {
                        if (jcrypt.crypt(passCode.get(i)[0], letter + dictWord).compareTo(passCode.get(i)[1]) == 0) {
                            //foundPass(i, letter + dictWord);
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
        map.remove(key);

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
                List<List<String>> data = new ArrayList<>();
                List<String> linedata = new ArrayList<>();

                String[] hash = new String[2];
                String[] perLine = line.split(":");
                linedata.add(perLine[0]);
                linedata.add(perLine[4].split(" ")[0]);
                linedata.add(perLine[4].split(" ")[1]);
                linedata.add(perLine[1].substring(0, 2));
                data.add(linedata);
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

            while ((line = br.readLine()) != null) {
                dict.add(line);
                dict.add(line.toUpperCase());
                String _first = line.substring(0, line.length() - 1);
                String _last = line.substring(1, line.length());
                dict.add(_first);
                dict.add(_last);
                if (line.length() > 2 && line.length() < 9) {
                    dict.add(_first.substring(0, 1) + _first.substring(1, _first.length()).toUpperCase());
                    dict.add(_first.substring(0, 1).toUpperCase() + _first.substring(1, _first.length()));
                    dict.add(new StringBuffer(_first.substring(0, 1).toUpperCase() + _first.substring(1, _first.length())).reverse().toString());

                    dict.add(_last.substring(0, 1) + _last.substring(1, _last.length()).toUpperCase());
                    dict.add(_last.substring(0, 1).toUpperCase() + _last.substring(1, _last.length()));
                    dict.add(new StringBuffer(_last.substring(0, 1).toUpperCase() + _last.substring(1, _last.length())).reverse().toString());

                    dict.add(line.substring(0, 1) + line.substring(1, line.length()).toUpperCase());
                    dict.add(line.substring(0, 1).toUpperCase() + line.substring(1, line.length()));
                    dict.add(new StringBuffer(line.substring(0, 1).toUpperCase() + line.substring(1, line.length())).reverse().toString());

                }
                dict.add(line + line);

                String reversed = new StringBuffer(line).reverse().toString();
                dict.add(reversed);
                if (line.length() < 8) {
                    dict.add(line + reversed);
                    dict.add(reversed + line);
                }
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
