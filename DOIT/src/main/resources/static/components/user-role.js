export default Vue.component('user-role', {
    props: {
        role: {
            type: Object,
            required: true
        }
    },
    template: 
    /*html*/`
    <div class="card border-info mb-3" style="margin-top: 10px">
        <div class="card-body">
            <h5 class="card-title">{{role.type}}</h5>
            <div style="padding: 1rem;">
                <p class="card-subtitle">categories</p>
                <div v-for="(category, index) in role.categories" :key="index" style="padding-top: 10px">
                    <button @click="goCategory(category.name)" class="btn btn-outline-info" style="width: 100%;">{{category.name}}</button>
                </div>
            </div>
            <div style="padding: 1rem;" v-show="role.projects.length!=0">
                <p class="card-subtitle">projects</p>
                <div v-for="(project, index) in role.projects" :key="index" style="padding-top: 10px">
                    <button @click="goProject(project.id)" class="btn btn-outline-info" style="width: 100%;">{{project.name}}</button>
                </div>
            </div>
            <div style="padding: 1rem;" v-show="role.history.length!=0">
                <p class="card-subtitle">history</p>
                <div v-for="(project, index) in role.history" :key="index" style="padding-top: 10px">
                    <button @click="goProject(project.id)" class="btn btn-outline-info" style="width: 100%;">
                    <p style="flex: 1;">{{project.name}}</p>
                    <p v-show="role.type=='designer' && role.evaluations!=undefined && role.evaluations.length!=0" class="card-subtitle">rating: {{getEvaluation(project.id)}}</p>
                    </button>
                </div>
            </div>
            <div style="padding: 1rem;" v-show="role.type=='designer' && role.curriculumVitae.length!=0">
                <p class="card-subtitle">experiences</p>
                <div v-for="(experience, index) in role.curriculumVitae" :key="index">
                    <div class="card border-info mb-3" style="margin-top: 10px">
                        <div class="card-header" style="text-align: center">
                            <h5>{{experience.dateStart+' - '+experience.dateStop}}</h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text">{{experience.experience}}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
        `,
        methods: {
            goProject(id) {
                this.$router.push('/project/'+id);
            },
            goCategory(id) {
                this.$router.push('/category/'+id);
            },
            back() {
                this.$router.go(-1);
            },
            getEvaluation(id) {
                if(this.role.type=='designer') {
                    var res = this.role.evaluations.find(e => e.projectId == id);
                    return res?res.evaluation:'';
                }
            }
        }
});