Softwarewolves-java-player
==========================

This is an example bot implementation in java for the softwarewolves game. This player implements the "lazy villager" strategy.

More information on the softwarewolves game can be found at : [Softwarewolves documentation][1].

Setting up the project
---------------------

1. Get the code. With github, there are several possibilities:
  * Download the project as a zipfile from github (github button somewhere on page). 
  * Fork the project to your own github repository (github button somewhere on page), then clone it. This requires a github account.
  * Clone the repository to your own computer. This requires git to be installed on your system. For cloning, you can use your favorite git tool or the following command:

```
        git clone https://github.com/supernelis/softwarewolves-java-player.git 
```

2. Get the project in your favorite editor.
  * for Eclipse addicts: 
    * New java project
    * Uncheck the checkbox "Use default location" and point to where you checked out the java code

3. Add the smack jar files to your buildpath (all 4 of them)

4. Run the main method in the class softwaresolves.javabot.Bot

Smack
--------------------

This project uses the smack libraries for xmpp. Although it is not considered as good practice, 
we included the documentation and libraries of of smack in this git project to simplify setup, limit the 
amount of external dependencies that can fail and simplify offline working.

- Documentation: <a href="smack_3_2_2/documentation/">Offline</a>, [jive documentation website][2].
- API: <a href="smack_3_2_2/javadoc/">Offline</a>, [jive javadoc website][3].

[1]: https://github.com/supernelis/softwarewolves-doc
[2]: http://www.igniterealtime.org/builds/smack/docs/latest/documentation/
[3]: http://www.igniterealtime.org/builds/smack/docs/latest/javadoc/
