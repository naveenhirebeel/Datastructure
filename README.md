1. Design Instagram
    Photo update -
    Store photos in Distributed file system and update metadata, use CDN to deliver photos at different geographical locations.  
    Feed ->
   1. 	For every post, event will be triggered, FeedService will listen to that event and build a distributed cache for all the followers. As soon user logs in his readily available feed will be served from cache.
   2.	For celebrity users(Users having lot of followers(1M+), it is not practical to build cache for all users, hence we have to go for hybrid solution where we will not populate cache for celebrity users posts instead we just populate cache for normal users post. Whenever user logs in we just pull posts from celebrity users and combine with normal followers post for the logged in person and feed page will be populated.
   3. 	In case of cache miss,  DB call is the option but if cache miss happens to lot of people then subsequent DB calls will be executed and which will increase the load. In such cache miss cache we should come up with Promise based cache and all users should be listening to Promise based cache and one DB call is enough and one completion all users will get the feed. 