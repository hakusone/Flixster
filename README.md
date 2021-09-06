# Flix
Flix is an app that allows users to browse movies from the [The Movie Database API](http://docs.themoviedb.apiary.io/#).

## Flix Part 2

### User Stories

#### REQUIRED (10pts)

- [x] (8pts) Expose details of movie (ratings using RatingBar, popularity, and synopsis) in a separate activity.
- [x] (2pts) Allow video posts to be played in full-screen using the YouTubePlayerView.

#### BONUS

- [x] Implement a shared element transition when user clicks into the details of a movie (1 point).
- [x] Trailers for popular movies are played automatically when the movie is selected (1 point).
    - [x] When clicking on a popular movie (i.e. a movie voted for more than 5 stars) the video should be played immediately.
    - [x] Less popular videos rely on the detailed page should show an image preview that can initiate playing a YouTube video.
- [x] Add a play icon overlay to popular movies to indicate that the movie can be played (1 point).
- [x] Apply data binding for views to help remove boilerplate code. (1 point)
- [x] Add a rounded corners for the images using the Glide transformations. (1 point)

### App Walkthough GIF

`TODO://` Add the URL to your animated app walkthough `gif` in the image tag below, `YOUR_GIF_URL_HERE`. Make sure the gif actually renders and animates when viewing this README. (🚫 Remove this paragraph after after adding gif)

<img src="YOUR_GIF_URL_HERE" width=250><br>

### Notes

Describe any challenges encountered while building the app.

## Open-source libraries used
- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

---

## Flix Part 1

### User Stories
#### REQUIRED (10pts)
- [x] (10pts) User can view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API.

#### BONUS
- [x] (2pts) Views should be responsive for both landscape/portrait mode.
    - [x] (1pt) In portrait mode, the poster image, title, and movie overview is shown.
    - [x] (1pt) In landscape mode, the rotated alternate layout should use the backdrop image instead and show the title and movie overview to the right of it.

- [ ] (2pts) Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
- [x] (2pts) Improved the user interface by experimenting with styling and coloring.
- [x] (2pts) For popular movies (i.e. a movie voted for more than 5 stars), the full backdrop image is displayed. Otherwise, a poster image, the movie title, and overview is listed. Use Heterogenous RecyclerViews and use different ViewHolder layout files for popular movies and less popular ones.

### App Walkthough GIF

Walkthrough for viewing a list of movies currently playing in theaters from the Movie Database API, as well as additional styling and full backdrop image for popular movies

<img src='walkthrough_portrait.gif' title='Video Walkthrough Portrait' width='250' alt='Video Walkthrough Portrait' />

Walkthrough for responsive landscape and portrait views

<img src='walkthrough_convert.gif' title='Video Walkthrough Convert' width='400' alt='Video Walkthrough Convert' />

<img src='walkthrough_land.gif' title='Video Walkthrough Landscape' width='400' alt='Video Walkthrough Landscape' />

Walkthrough for default placeholder graphic during loading

<img src='walkthrough_placeholder.gif' title='Video Walkthrough Placeholder' width='250' alt='Video Walkthrough Placeholder' />

Gifs created with [Kap](https://getkap.co/)


### Notes
I made the most popular movies require over 8 stars, because most of the movies were over 5 stars (except for one), and it wouldn't display some of the user stories well.

I had difficulty capturing the placeholder image in action, because the app liked to load all at once. Technically, I did implement it and I did see it sometimes, but not consistently to capture in a gif.

Edit: Tried to make placeholder work. Things I did include: setting different network speeds in the Virtual Device Manager, making configuration a separate get request (instead of hardcoding the url), and attempted to clear Glide's memory and disk cache (but failed). I think Glide's cache'ing images, so it never actually fetches the images from the url.

### Open-source libraries used

- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Androids