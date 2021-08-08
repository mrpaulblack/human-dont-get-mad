# Human Dont Get Mad

A ludo clone written in java 1.8 with only standard librarys and org.json.
The eclipse project consists of a client (client.Main) and a corresponding server (server.Server)
and useses the MAEDN protocol for unified data transfer over TCP sockets in json format: 
https://gitlab.imn.htwk-leipzig.de/aop-pr-fung/maedn-protocol.



## Deveolpment

* Clone the repo: `git clone https://github.com/MrPaulBlack/human-dont-getmad`

* Import the project as an existing project into eclipse (you can also use the build in git tool, but you are probalby not going to have a great time)

The project requires Java 1.8 at minimum; The needed `org.json` lib depency is already part of the repo.



## Server usage

* You can download the most recent server from the release page: https://github.com/MrPaulBlack/human-dont-get-mad/releases

* Start the server on 0.0.0.0:2342: `java -jar hdgm-server{version}.jar`

* The following args for starting the server are supported right now: `--help`, `--log {log-level}`, `--port {port}`

The server uses json to talk to clients and opens a thread managed through a thread pool for every connection. Since it supports the MAEDN protocol it also supports
all other clients using the same protocol.



## Client Usage

* You can download the most recent server from the release page: https://github.com/MrPaulBlack/human-dont-get-mad/releases

* You can start the client like this from the terminal: `java -jar hdgm-server{version}.jar`

* After that you can just connect to a running server and player the game!

The client GUI is completly written in Swing and therofore only requires java std libs.



### Contributors

- [Chikseen](@Chikseen)

- [UrsusNix](@UrsusNix)

- [MrPaulBlack](@MrPaulBlack)
