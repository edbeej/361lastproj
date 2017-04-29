UTEID: eab3777;
FIRSTNAME: Edwin;
LASTNAME: Bejarano;
CSACCOUNT: johnny; brad;
EMAIL: edwin.bejarano@utexas.edu

[Program 6]
[Description]
There are 2 java files: The main part of the password cracker is in PasswordCrack.java and the encryption is in jcrypt.java.
Initially the program will grab the word ditionary from the file given to it and will store that dictionary to be later
used in comparing to the hashed. As I am getting the words from the dictionary I am mangling the basic string mangles
like reverse, all caps, reflection, reverse reflection

[Finish]
We found 19/20 on passwd1 in time 120 seconds. We found 15/20 on passwd2 in time 271 seconds.

[Test Case 1]

[Input]

michael:atbWfKL4etk4U:500:500:Michael Ferris:/home/michael:/bin/bash
abigail:&i4KZ5wmac566:501:501:Abigail Smith:/home/abigail:/bin/tcsh
samantha:(bUx9LiAcW8As:502:502:Samantha Connelly:/home/samantha:/bin/bash
tyler:<qt0.GlIrXuKs:503:503:Tyler Jones:/home/tyler:/bin/tcsh
alexander:feohQuHOnMKGE:504:504:Alexander Brown:/home/alexander:
james:{ztmy9azKzZgU:505:505:James Dover:/home/james:/bin/bash
benjamin:%xPBzF/TclHvg:506:506:Benjamin Ewing:/home/benjamin:/bin/bash
morgan:khnVjlJN3Lyh2:507:507:Morgan Simmons:/home/morgan:/bin/bash
jennifer:e4DBHapAtnjGk:508:508:Jennifer Elmer:/home/jennifer:/bin/bash
nicole:7we09tBSVT76o:509:509:Nicole Rizzo:/home/nicole:/bin/tcsh
evan:3dIJJXzELzcRE:510:510:Evan Whitney:/home/evan:/bin/bash
jack:jsQGVbJ.IiEvE:511:511:Jack Gibson:/home/jack:/bin/bash
victor:w@EbBlXGLTue6:512:512:Victor Esperanza:/home/victor:
caleb:8joIBJaXJvTd2:513:513:Caleb Patterson:/home/caleb:/bin/bash
nathan:nxsr/UAKmKnvo:514:514:Nathan Moore:/home/nathan:/bin/ksh
connor:gwjT8yTnSCVQo:515:515:Connor Larson:/home/connor:/bin/bash
rachel:KelgNcBOZdHmA:516:516:Rachel Saxon:/home/rachel:/bin/bash
dustin:5WW698tSZJE9I:517:517:Dustin Hart:/home/dustin:/bin/csh
maria:!cI6tOT/9D2r6:518:518:Maia Salizar:/home/maria:/bin/zsh
paige:T8jwuve9rQBo.:519:519:Paige Reiser:/home/paige:/bin/bash

[Output]

Building initial Dictionaries......
Built initial Dictionaries: 39
Found 1/20
Found password for Abigail Smith, the password was 'liagiba', found in 0 seconds
Found 2/20
Found password for Maia Salizar, the password was 'Salizar', found in 0 seconds
Found 3/20
Found password for Michael Ferris, the password was 'michael', found in 0 seconds
Starting threads
Found 4/20
Found password for Dustin Hart, the password was 'litpeR', found in 1 seconds
Found 5/20
Found password for Alexander Brown, the password was 'squadro', found in 1 seconds
Found 6/20
Found password for Samantha Connelly, the password was 'amazing', found in 1 seconds
Found 7/20
Found password for Victor Esperanza, the password was 'THIRTY', found in 2 seconds
Found 8/20
Found password for Caleb Patterson, the password was 'teserP', found in 2 seconds
Found 9/20
Found password for Tyler Jones, the password was 'eeffoc', found in 3 seconds
Found 10/20
Found password for Evan Whitney, the password was 'Impact', found in 3 seconds
Found 11/20
Found password for Jennifer Elmer, the password was 'doorrood', found in 5 seconds
Found 12/20
Found password for Nicole Rizzo, the password was 'keyskeys', found in 6 seconds
Found 13/20
Found password for James Dover, the password was 'icious', found in 6 seconds
Found 14/20
Found password for Connor Larson, the password was 'enoggone', found in 7 seconds
Found 15/20
Found password for Jack Gibson, the password was 'sATCHEL', found in 7 seconds
Found 16/20
Found password for Nathan Moore, the password was 'sHREWDq', found in 61 seconds
Found 17/20
Found password for Benjamin Ewing, the password was 'abort6', found in 67 seconds
Found 18/20
Found password for Rachel Saxon, the password was 'obliqu3', found in 96 seconds
Found 19/20
Found password for Morgan Simmons, the password was 'rdoctor', found in 132 seconds

In total, I can crack 19/20 password in time 132 seconds.
I can not crack 1/20 password, the list is
paige


[Test Case 2]

[Input]

michael:atQhiiJLsT6cs:500:500:Michael Ferris:/home/michael:/bin/bash
abigail:&ileDTgJtzCRo:501:501:Abigail Smith:/home/abigail:/bin/tcsh
samantha:(bt0xiAwCf7nA:502:502:Samantha Connelly:/home/samantha:/bin/bash
tyler:<qf.L9z1/tZkA:503:503:Tyler Jones:/home/tyler:/bin/tcsh
alexander:fe8PnYhq6WoOQ:504:504:Alexander Brown:/home/alexander:
james:{zQOjvJcHMs7w:505:505:James Dover:/home/james:/bin/bash
benjamin:%xqFrM5RVA6t6:506:506:Benjamin Ewing:/home/benjamin:/bin/bash
morgan:kh/1uC5W6nDKc:507:507:Morgan Simmons:/home/morgan:/bin/bash
jennifer:e4EyEMhNzYLJU:508:508:Jennifer Elmer:/home/jennifer:/bin/bash
nicole:7wKTWsgNJj6ac:509:509:Nicole Rizzo:/home/nicole:/bin/tcsh
evan:3d1OgBYfvEtfg:510:510:Evan Whitney:/home/evan:/bin/bash
jack:js5ctN1leUABo:511:511:Jack Gibson:/home/jack:/bin/bash
victor:w@FxBZv.d0y/U:512:512:Victor Esperanza:/home/victor:
caleb:8jGWbU0xbKz06:513:513:Caleb Patterson:/home/caleb:/bin/bash
nathan:nxr9OOqvZjbGs:514:514:Nathan Moore:/home/nathan:/bin/ksh
connor:gw9oXmw1L08RM:515:515:Connor Larson:/home/connor:/bin/bash
rachel:KenK1CTDGr/7k:516:516:Rachel Saxon:/home/rachel:/bin/bash
dustin:5Wb2Uqxhoyqfg:517:517:Dustin Hart:/home/dustin:/bin/csh
maria:!cSaQELm/EBV.:518:518:Maia Salizar:/home/maria:/bin/zsh
paige:T8U5jQaDVv/o2:519:519:Paige Reiser:/home/paige:/bin/bash

[Output]

Building initial Dictionaries......
Built initial Dictionaries: 39
Starting threads
Found 1/20
Found password for Alexander Brown, the password was 'Lacque', found in 1 seconds
Found 2/20
Found password for Nicole Rizzo, the password was 'INDIGNIT', found in 3 seconds
Found 3/20
Found password for Jack Gibson, the password was 'ellows', found in 6 seconds
Found 4/20
Found password for Abigail Smith, the password was 'Saxon', found in 6 seconds
Found 5/20
Found password for Morgan Simmons, the password was 'dIAMETER', found in 6 seconds
Found 6/20
Found password for Tyler Jones, the password was 'eltneg', found in 8 seconds
Found 7/20
Found password for Michael Ferris, the password was 'tremors', found in 8 seconds
Found 8/20
Found password for Caleb Patterson, the password was 'zoossooz', found in 8 seconds
Found 9/20
Found password for Benjamin Ewing, the password was 'soozzoos', found in 8 seconds
Found 10/20
Found password for James Dover, the password was 'enchant$', found in 28 seconds
Found 11/20
Found password for Connor Larson, the password was 'nosral', found in 37 seconds
Found 12/20
Found password for Maia Salizar, the password was 'SpatteR', found in 85 seconds
Found 13/20
Found password for Dustin Hart, the password was 'Swine3', found in 121 seconds
Found 14/20
Found password for Nathan Moore, the password was 'uPLIFTr', found in 212 seconds
Found 15/20
Found password for Evan Whitney, the password was '^bribed', found in 270 seconds


In total, I can crack 15/20 password in time 270 seconds.
I can not crack 5/20 password, the list is

paige