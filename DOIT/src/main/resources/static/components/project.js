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
                <h5 class="card-title" @click="goCategory(project.category.name)">{{project.category.name}}</h5>
              </div>
              <div class="card-body">
                <h5 class="card-title">{{project.projectState.name}}</h5>
                <p class="card-text">{{project.projectState.description}}</p>
              </div>
              <div class="card-body">
                <h5 class="card-title">Team</h5>
                <p class="card-text">{{project.team.open?"open":"close"}}</p>
                <p class="card-text" @click="goUser(project.team.projectProposer.idUser)">project proposer</p>
                <p v-show="project.team.programManager" class="card-text" @click="goUser(project.team.programManager.idUser)">program manager</p>
                <p v-show="project.team.projectManager" class="card-text" @click="goUser(project.team.projectManager.idUser)">project manager</p>
                <p v-for="(el, index) in project.team.designers" :key="index" class="card-text" @click="goUser(el.idUser)">Designer {{index}}</p>
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