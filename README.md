# Twitter-board

* twb-client (port 8080) -- Java
  * twitter-board (twb) verticle starts up, and reads its configuration from a file
  * twb-client creates a twitter client, but does not connect
  * twb starts an HTTML server, and registers clients to:
  * update topics (initially there are none).  When topics are saved, 
  the twitter client is given the topics, and the stream is started.
  * if the stream was already open, need to close it, kill the client, and 
  re-initialize it with the new topics to watch, then opens the stream.
  * the responses from Twitter are posted on the message bus.
  * (future) allow users to register to receive notifications when new posts arrive

* twb-persist -- Java
  * listens to topics on the message bus, and persists them to the database
  * it also logs new posts to the screen
  
* twb-board (port 8000) -- JavaScript?
  * starts a server, subscribes to topics, and has a page that displays a
  rolling board, with new messages at the top
  * has configuration page to reset the size of the board
  * has a page that can retrieve historical topics from database, and 
  display them to users by paging.
  * future: search historical entries
    
