self.addEventListener('install', function(event) {
    event.waitUntil(
        caches.open('offline-media').then(function(cache) {
            return cache.addAll([
                'https://unpkg.com/vue/dist/vue.min.js',
                'https://unpkg.com/vue-router/dist/vue-router.min.js',
                'https://bootswatch.com/4/minty/bootstrap.css',
                'https://cdnjs.cloudflare.com/ajax/libs/blueimp-md5/2.18.0/js/md5.min.js',
                '/img.jpg',
                '/style.css',
                '/',
                '/index.html',
                '/manifest.json',
                '/router.js',
                '/sw.js',
                '/icon192.png',
                '/icon512.png',
                '/components/category.js',
                '/components/create-project.js',
                '/components/designer.js',
                '/components/evaluate.js',
                '/components/history.js',
                '/components/home.js',
                '/components/list-categories.js',
                '/components/list-my-pr.js',
                '/components/list-notifications.js',
                '/components/list-projects.js',
                '/components/login.js',
                '/components/manage-category.js',
                '/components/manage-experience.js',
                '/components/manage-pr.js',
                '/components/manage-project-state.js',
                '/components/program-manager.js',
                '/components/project-manager.js',
                '/components/project-proposer.js',
                '/components/project.js',
                '/components/remove-project-pjm.js',
                '/components/remove-project.js',
                '/components/role.js',
                '/components/search.js',
                '/components/send-pr.js',
                '/components/set-project-manager.js',
                '/components/signin.js',
                '/components/team-rec.js',
                '/components/user-main.js',
                '/components/user-role.js',
                '/components/user.js'
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