export default Vue.component('project-proposer', {
    template: `
    <div class='container'>
        <role :role="role"></role>
        <ul>
            <li v-for="(el, index) in list" :key="index">
                <div @click="go(index)">
                    <button>Create project</button>
                </div>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            role: undefined,
            list: [
                {path: '/create-project/'+this.$route.params.user}
            ]
        }
    },
    created() {
        this.$emit('load',true);
        fetch('/api/role/'+this.$route.params.user+'/project-proposer').then(res=>res.json()).then(res=>{
            this.role = res;
            this.$emit('load',false);
        });
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i]);
        }
    }
});