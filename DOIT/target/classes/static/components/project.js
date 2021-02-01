export default Vue.component('project', {
  template: 
  /*html*/`
  <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
      <div>
          <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
      </div>
      <div class="card border-info mb-3" style="margin-top: 10px">
            <div class="card-header" style="text-align: center">{{project.name}}</div>
            <div class="card-body">
              <h5 class="card-title">Description</h5>
              <p class="card-text">{{project.description}}</p>
            </div>
            <div class="card-body">
              <h5 class="card-title">Category</h5>
              <button class="btn btn-outline-info" style="width: 100%;" @click="goCategory(project.category.name)">{{project.category.name}}</button>
            </div>
            <div class="card-body">
              <h5 class="card-title">{{project.projectState.name}}</h5>
              <p class="card-text">{{project.projectState.description}}</p>
            </div>
            <div class="card-body">
              <h5 class="card-title">Team</h5>
              <p class="card-text">Registrations: {{project.team.open?"open":"close"}}</p>
              <button class="btn btn-outline-info" style="width: 100%; margin-bottom: 10px" @click="goUser(project.team.projectProposer.idUser)">project proposer</button>
              <button class="btn btn-outline-info" style="width: 100%; margin-bottom: 10px" @click="goUser(project.team.programManager.idUser)">program manager</button>
              <button class="btn btn-outline-info" style="width: 100%; margin-bottom: 10px" @click="goUser(project.team.projectManager.idUser)">project manager</button>
              <button class="btn btn-outline-info" style="width: 100%; margin-bottom: 5px" v-for="(el, index) in project.team.designers" :key="index" @click="goUser(el.idUser)">designer {{index}}</button>
            </div>
          </div>
      </div>
      `,
      data() {
          return {
              project: {}
          }
      },
      async created() {
          this.$emit('load',true);
          this.project = await (await fetch('/api/search/project-by-id?id='+this.$route.params.id)).json();
          this.$emit('load',false);
      },
      methods: {
        goUser(id) {
          this.$router.push('/user/'+id);
        },
        goCategory(id) {
          this.$router.push('/category/'+id);
        },
        back() {
            this.$router.go(-1);
        }
      }
});