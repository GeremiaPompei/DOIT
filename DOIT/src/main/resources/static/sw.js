self.addEventListener('install', function(event) {
    event.waitUntil(
        caches.open('offline-media').then(function(cache) {
            return cache.addAll([
                'https://unpkg.com/vue/dist/vue.min.js',
                'https://unpkg.com/vue-router/dist/vue-router.min.js',
                /*'/',
                '/index.html',
                '/manifest.json',
                '/style.css',
                '/router.js',
                '/sw.js',
                '/icon192.png',
                '/icon512.png'*/
            ]);
        })
    );
});

self.addEventListener('fetch', function(event) {
  event.respondWith(
    caches.match(event.request)
      .then(function(response) {
        if (response) {
          return response;
        }
        return fetch(event.request);
      }
    )
  );
});
