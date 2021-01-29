export default Vue.component('project', {
    template: `
        <div class='container'>
            <button @click="back()" class="back"><i style="font-size:24px" class="fa">&#xf104;</i></button>
            <p>Name</p>
            <h3 class="el">{{project.name}}</h3>
            <p>Description</p>
            <h3 class="el">{{project.description}}</h3>
            <p>Category</p>
            <h3 class="el">{{project.category.name}}</h3>
            <p>State</p>
            <h3 class="el">{{project.projectState.name}}</h3>
            <p>Team</p>
            <h3 class="el">{{project.team.open}}</h3>
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