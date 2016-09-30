# Twitter-board

A simple app written to learn vertx.  Basically consume tweets on various topics, persist them, and shown
them to users.  First verticle gets tweets and writes them to the us, another reads, persists and probably filters tweets,
and another would deal with showing them to users.

## An idea where this might be going

* TwitterVerticle (port 8080) -- Java
  * TwitterVerticle (twv) starts up, and reads its configuration from a file
  * twv creates a twitter client, but does not connect
  * twv starts an HTTML server, and registers clients to:
  * twv update topics (initially there are none).  When topics are saved, 
  the twitter client is given the topics, and the stream is started.
  * if the stream was already open, need to close it, kill the client, and 
  re-initialize it with the new topics to watch, then opens the stream.
  * the responses from Twitter are posted on the message bus.
  * (future) allow users to register to receive notifications when new posts arrive

* twv-persist -- Java
  * listens to topics on the message bus, and persists them to the database
  * it also logs new posts to the screen
  
* twv-board (port 8000) -- JavaScript?
  * starts a server, subscribes to topics, and has a page that displays a
  rolling board, with new messages at the top
  * has configuration page to reset the size of the board
  * has a page that can retrieve historical topics from database, and 
  display them to users by paging.
  * future: search historical entries
    
