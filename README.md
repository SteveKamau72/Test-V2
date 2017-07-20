# Test-V2
# Challenge
Add a button labelled "GITHUB"
if the button GitHub is pressed, using the github public api, display a list of current trending GitHub repositories.

# Problem faced
The Official Github API has was no support for Trending Projects. GitHub seems to use their API to write the trending page and don't present it back as a particular API. So i used the [Repository Search API](https://developer.github.com/v3/search/#search-repositories). I combined the search parameters as:

q = get the repositories recently created within a week

sort = by the number of stars

order = descending

# Extras:
1. Used [Glide Image Loading library](https://github.com/bumptech/glide). Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.

2. Implemented [loopj Asynchronous Http Client](http://loopj.com/android-async-http/), an asynchronous callback-based Http client for Android built on top of Apache’s HttpClient libraries. 

3. Used [ButterKnife](http://jakewharton.github.io/butterknife/). A field and method binding for Android views which uses annotation processing to generate boilerplate code for you.

4. For analytics and crash reporting i used [Firebase Analytics](https://firebase.google.com/docs/analytics/android/start/) and [Firebase Crash Reporting](https://firebase.google.com/docs/crash/)
