1. Design Instagram

    Photo update -
    Store photos in Distributed file system and update metadata, use CDN to deliver photos at different geographical locations.  
    Feed ->
   1. 	For every post, event will be triggered, FeedService will listen to that event and build a distributed cache for all the followers. As soon user logs in his readily available feed will be served from cache.
   2.	For celebrity users(Users having lot of followers(1M+), it is not practical to build cache for all users, hence we have to go for hybrid solution where we will not populate cache for celebrity users posts instead we just populate cache for normal users post. Whenever user logs in we just pull posts from celebrity users and combine with normal followers post for the logged in person and feed page will be populated.
   3. 	In case of cache miss,  DB call is the option but if cache miss happens to lot of people then subsequent DB calls will be executed and which will increase the load. In such cache miss cache we should come up with Promise based cache and all users should be listening to Promise based cache and one DB call is enough and one completion all users will get the feed.

2. Design Tiny URL

    Get the traffic volume to serve. 62^n is the common formula to calculate volume to serve, here n is length of url. Ex: 62^7 ~= 3500 billion URLs.

    DB
    id, short_url, long_url

    Approaches
    1. Generate random url and save.
        Pro: simple working solution
        Disadvantage: High chance of collision
    2. Generate random URL & insert, if collision retry again till it succeed.
        Pro: Simple and avoids collision
        Cons: Not practical solution when serving distributed system.
    3. Approach 3: Generate ID from workerID(7Bit), TimeStamp(43 bit till milliseconds), Sequence/Random number(8bit) 
    - This is more than 62^7 I.e. 2^58 is more than 62^7 hence not useful. If you reduce the bits then more chance of duplicates. This approach guaranty less duplicates but it’s not bug free.
    
   4. Approach 4: Insert into DB every time and get unique id and update short url.
    - Pros: bug free
    - Cons: not suitable for distributed system as there is bottleneck on Insert & update.
   
   5. Approach 5: Range numbers,   How it works: ID generation service - Every worker id(Totally 64 workers) will
        How it works: ID generation service - Every worker id(Totally 64 workers) will initially get range from DB(Say 
        million users once), for every subsequent request get one count from range and respond. With this there is 
        always unique numbers generated. Id generation can be separate service or it can be generated within same short 
        url generation service. Insert into DB next range used as below and use auto_increment column to generate range while inserting as we can know from query what is next id and use that number to generate range and mark it as used.
        Id, range, status, workerID
        1, 1M, Used, 1 2, 1M to 2M Used, 64
        .
        .
        Pro: Server more traffic within distributed system.
        Cons: In case of loss of worker machine, we will loos million unique ids which we retrieved from DB. 


    https://leetcode.com/discuss/interview-question/124658/Design-a-URL-Shortener-(-TinyURL-)-System/
    https://www.geeksforgeeks.org/how-to-design-a-tiny-url-or-url-shortener/
    https://www.youtube.com/watch?v=fMZMm_0ZhK4&t=253s
    
3. Rate limit

<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>A rate limit system is a key component in any application or system that handles a large volume of requests from multiple sources. It is responsible for ensuring that the system remains stable and performs well, by limiting the rate at which incoming requests are processed.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Here are some key considerations for designing a rate limit system:</p>
<ol start="1" style="margin-bottom:0cm;margin-top:0cm;" type="1">
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Determine the scope: Decide what resources or endpoints need to be rate-limited, and what constitutes excessive requests.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Decide on the rate limit algorithm: There are several rate-limiting algorithms, including token bucket, leaky bucket, and fixed window. Choose the algorithm that best suits your needs based on factors such as scalability, accuracy, and ease of implementation.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Set the rate limit: Based on the algorithm you have chosen, decide on the maximum number of requests that can be processed within a given time window (e.g., 100 requests per minute).</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Implement the rate limit: You can implement the rate limit system within your application code or use a third-party service. If you implement it yourself, ensure that you use a distributed system to handle rate limiting across multiple instances of your application.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Provide feedback to users: When a user exceeds the rate limit, provide meaningful feedback to inform them of the reason for the rate limit and when they can expect to be able to send requests again.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Monitor and tune: Continuously monitor the performance of your rate limit system and adjust the settings as needed to optimize performance and prevent abuse.</li>
</ol>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Overall, designing a rate limit system requires careful consideration of the scope, algorithm, rate limit settings, implementation, feedback to users, and ongoing monitoring and tuning to ensure optimal performance.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Token Bucket, Leaky Bucket, Fixed Window, and Sliding Window are all algorithms used for rate limiting incoming requests.</p>
<ol start="1" style="margin-bottom:0cm;margin-top:0cm;" type="1">
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Token Bucket Algorithm: This algorithm works by maintaining a &quot;token bucket&quot; that is filled with a fixed number of tokens over time. Each incoming request consumes one token from the bucket, and if there are no tokens left, the request is rejected. This algorithm is useful for handling bursts of traffic as unused tokens are stored in the bucket, allowing for bursts of traffic to be processed quickly.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Leaky Bucket Algorithm: This algorithm works by imagining a &quot;bucket&quot; that can hold a fixed amount of water. Water is added to the bucket at a fixed rate, but if the bucket overflows, the excess water is discarded. Similarly, requests arrive at a fixed rate and are processed if there is enough &quot;water&quot; (or capacity) in the bucket. This algorithm is useful for ensuring a consistent rate of traffic.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Fixed Window Algorithm: This algorithm divides time into fixed intervals (e.g., 1 second), and each interval has a maximum number of requests that can be processed. Once the limit is reached, any further requests are rejected. This algorithm is simple to implement but can be less effective in handling bursts of traffic.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Sliding Window Algorithm: This algorithm is similar to the fixed window algorithm but uses a sliding time window. A sliding window divides time into fixed intervals, and a new window starts every time interval. Requests that arrive during a window are processed, and once the limit is reached, any further requests are rejected until the next window starts. This algorithm provides better handling of bursts of traffic as it considers requests over a rolling time window.</li>
</ol>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Overall, the choice of algorithm depends on the specific requirements of the application or system and the trade-offs between factors such as accuracy, scalability, and ease of implementation.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Both fixed window and sliding window rate limiters are used to limit the rate of incoming requests to a system or application, but they differ in how they define and enforce time intervals.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>The fixed window rate limiter divides time into fixed intervals (e.g., 1 second) and sets a limit on the maximum number of requests that can be processed within each interval. Once the limit is reached, any further requests are rejected until the next interval starts. For example, if the limit is set to 100 requests per second, and 110 requests arrive in the first second, 100 requests will be processed, and the remaining 10 requests will be rejected.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>The sliding window rate limiter also divides time into intervals, but the intervals slide forward over time. For example, if the interval is set to 1 second and the limit is 100 requests per second, the rate limiter will maintain a moving window that looks back over the previous second and enforces a limit of 100 requests within that window. Any requests that exceed the limit within the sliding window will be rejected. This approach allows for more flexible and precise rate limiting, as it considers the number of requests over a rolling time window instead of discrete time intervals.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>In summary, fixed window rate limiters provide a simple and easy-to-implement approach to rate limiting, while sliding window rate limiters provide more granular control over the rate of incoming requests and are better suited to handle bursts of traffic. The choice of rate limiter depends on the specific requirements of the application or system and the trade-offs between factors such as accuracy, scalability, and ease of implementation.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>

[rate limit.docx](..%2F..%2FDesign%2Frate%20limit.docx)