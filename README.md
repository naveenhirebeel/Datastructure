<ol style="list-style-type: undefined;margin-left:8px;">
    <li>Design Instagram<ol style="list-style-type: lower-alpha;">
            <li>Photo Update</li>
        </ol>
    </li>
</ol>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;margin-left:36.0pt;text-indent:36.0pt;'>Store photos in Distributed file system and update metadata, use CDN to deliver photos at different geographical locations.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;margin-left:36.0pt;'>&nbsp;</p>
<ol style="list-style-type: lower-alpha;">
    <li>Feed<ol style="list-style-type: lower-alpha;">
            <li>For every post, event will be triggered, FeedService will listen to that event and build a distributed cache for all the followers. As soon user logs in his readily available feed will be served from cache.</li>
            <li>For celebrity users(Users having lot of followers(1M+), it is not practical to build cache for all users, hence we have to go for hybrid solution where we will not populate cache for celebrity users posts instead we just populate cache for normal users post. Whenever user logs in we just pull posts from celebrity users and combine with normal followers post for the logged in person and feed page will be populated.</li>
            <li>In case of cache miss, DB call is the option but if cache miss happens to lot of people then subsequent DB calls will be executed and which will increase the load. In such cache miss cache we should come up with Promise based cache and all users should be listening to Promise based cache and one DB call is enough and one completion all users will get the feed.</li>
        </ol>
    </li>
</ol>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<div style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>
    <ol style="margin-bottom:0cm;list-style-type: undefined;margin-left:8px;">
        <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Design Tiny URL</li>
    </ol>
</div>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Get the traffic volume to serve. 62^n is the common formula to calculate volume to serve, here n is length of url. Ex: 62^7 ~= 3500 billion URLs.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;DB</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;id, short_url, long_url</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Approaches</p>
<div style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>
    <ol style="margin-bottom:0cm;list-style-type: lower-alpha;">
        <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Generate random url and save.</li>
    </ol>
</div>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;text-indent:36.0pt;'>Pro: simple working solution</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;margin-left:18.0pt;text-indent:18.0pt;'>Disadvantage: High chance of collision</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;margin-left:18.0pt;text-indent:18.0pt;'>&nbsp;</p>
<div style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>
    <ol style="margin-bottom:0cm;list-style-type: lower-alpha;">
        <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Generate random URL &amp; insert, if collision retry again till it succeeds.<br>&nbsp;Pro: Simple and avoids collision.</li>
    </ol>
</div>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;margin-left:18.0pt;text-indent:18.0pt;'>Cos: Not practical solution when serving distributed system.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;margin-left:18.0pt;text-indent:18.0pt;'>&nbsp;</p>
<div style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>
    <ol style="margin-bottom:0cm;list-style-type: lower-alpha;">
        <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Generate ID from workerID(7Bit), TimeStamp(43 bit till milliseconds), Sequence/Random number(8bit)<br>&nbsp;This is more than 62^7 I.e. 2^58 is more than 62^7 hence not useful. If you reduce the bits then more chance of duplicates. This approach guaranty less duplicates but it&rsquo;s not bug free.</li>
    </ol>
</div>
<p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:36.0pt;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<div style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>
    <ol style="margin-bottom:0cm;list-style-type: lower-alpha;">
        <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Insert into DB every time and get unique id and update short url.<br>&nbsp;Pro: Bug free<br>&nbsp;Cons: Not suitable for distributed system as there is bottleneck on Insert &amp; update.</li>
    </ol>
</div>
<p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:36.0pt;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<div style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>
    <ol style="margin-bottom:0cm;list-style-type: lower-alpha;">
        <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Range numbers:&nbsp;<br>&nbsp;How It works -&nbsp;<br>&nbsp;ID generation service - Every worker id(Totally 64 workers) will initially get range from DB(Say&nbsp;million users once), for every subsequent request get one count from range and respond. With this there is always unique numbers generated. Id generation can be separate service or it can be generated within same short&nbsp;url generation service. Insert into DB next range used as below and use auto_increment column to generate range while inserting as we can know from query what is next id and use that number to generate range and mark it as used.<br>&nbsp;</li>
    </ol>
</div>
<table style="margin-left:36.0pt;border-collapse:collapse;border:none;">
    <tbody>
        <tr>
            <td style="width: 112.7pt;border: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>ID</p>
            </td>
            <td style="width: 112.7pt;border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Range</p>
            </td>
            <td style="width: 112.7pt;border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Status</p>
            </td>
            <td style="width: 112.7pt;border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Worker id</p>
            </td>
        </tr>
        <tr>
            <td style="width: 112.7pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>1</p>
            </td>
            <td style="width: 112.7pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>0 &ndash; 1M</p>
            </td>
            <td style="width: 112.7pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Used</p>
            </td>
            <td style="width: 112.7pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>1</p>
            </td>
        </tr>
        <tr>
            <td style="width: 112.7pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>2</p>
            </td>
            <td style="width: 112.7pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>1M &ndash; 2M</p>
            </td>
            <td style="width: 112.7pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Used</p>
            </td>
            <td style="width: 112.7pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;">
                <p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>64</p>
            </td>
        </tr>
    </tbody>
</table>
<p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:36.0pt;font-size:16px;font-family:"Calibri",sans-serif;'><br>&nbsp;Pro: Suitable for distributed system</p>
<p style='margin-top:0cm;margin-right:0cm;margin-bottom:0cm;margin-left:36.0pt;font-size:16px;font-family:"Calibri",sans-serif;'>Cons: In case of loss of worker machine, we will lose max 1Million of range<br>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<div style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>
    <ol style="margin-bottom:0cm;list-style-type: undefined;margin-left:8px;">
        <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Rate Limit &nbsp; &nbsp;&nbsp;</li>
    </ol>
</div>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
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
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Token Bucket, Leaky Bucket, Fixed Window, and Sliding Window are all algorithms used for rate limiting incoming requests.</p>
<ol start="1" style="margin-bottom:0cm;margin-top:0cm;" type="1">
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Token Bucket Algorithm: This algorithm works by maintaining a &quot;token bucket&quot; that is filled with a fixed number of tokens over time. Each incoming request consumes one token from the bucket, and if there are no tokens left, the request is rejected. This algorithm is useful for handling bursts of traffic as unused tokens are stored in the bucket, allowing for bursts of traffic to be processed quickly.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Leaky Bucket Algorithm: This algorithm works by imagining a &quot;bucket&quot; that can hold a fixed amount of water. Water is added to the bucket at a fixed rate, but if the bucket overflows, the excess water is discarded. Similarly, requests arrive at a fixed rate and are processed if there is enough &quot;water&quot; (or capacity) in the bucket. This algorithm is useful for ensuring a consistent rate of traffic.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Fixed Window Algorithm: This algorithm divides time into fixed intervals (e.g., 1 second), and each interval has a maximum number of requests that can be processed. Once the limit is reached, any further requests are rejected. This algorithm is simple to implement but can be less effective in handling bursts of traffic.</li>
    <li style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Sliding Window Algorithm: This algorithm is similar to the fixed window algorithm but uses a sliding time window. A sliding window divides time into fixed intervals, and a new window starts every time interval. Requests that arrive during a window are processed, and once the limit is reached, any further requests are rejected until the next window starts. This algorithm provides better handling of bursts of traffic as it considers requests over a rolling time window.</li>
</ol>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Overall, the choice of algorithm depends on the specific requirements of the application or system and the trade-offs between factors such as accuracy, scalability, and ease of implementation.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>Both fixed window and sliding window rate limiters are used to limit the rate of incoming requests to a system or application, but they differ in how they define and enforce time intervals.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>The fixed window rate limiter divides time into fixed intervals (e.g., 1 second) and sets a limit on the maximum number of requests that can be processed within each interval. Once the limit is reached, any further requests are rejected until the next interval starts. For example, if the limit is set to 100 requests per second, and 110 requests arrive in the first second, 100 requests will be processed, and the remaining 10 requests will be rejected.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>The sliding window rate limiter also divides time into intervals, but the intervals slide forward over time. For example, if the interval is set to 1 second and the limit is 100 requests per second, the rate limiter will maintain a moving window that looks back over the previous second and enforces a limit of 100 requests within that window. Any requests that exceed the limit within the sliding window will be rejected. This approach allows for more flexible and precise rate limiting, as it considers the number of requests over a rolling time window instead of discrete time intervals.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>In summary, fixed window rate limiters provide a simple and easy-to-implement approach to rate limiting, while sliding window rate limiters provide more granular control over the rate of incoming requests and are better suited to handle bursts of traffic. The choice of rate limiter depends on the specific requirements of the application or system and the trade-offs between factors such as accuracy, scalability, and ease of implementation.</p>
<p style='margin:0cm;font-size:16px;font-family:"Calibri",sans-serif;'>&nbsp;</p>