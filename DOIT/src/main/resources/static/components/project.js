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
                <p class="card-text">{{project.category}}</p>
              </div>
              <div class="card-body">
                <h5 class="card-title">Project state</h5>
                <p class="card-text">{{project.projectState}}</p>
              </div>
              <div class="card-body">
                <h5 class="card-title">Team</h5>
                <p class="card-text">{{project.team}}</p>
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
            back() {
                this.$router.go(-1);
            }
        }
});