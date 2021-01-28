export default Vue.component('project-proposer', {
    template: `
    <div class='container'>
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
                {path: '/create-project/'}
            ]
        }
    },
    created() {
        this.$emit('load',true);
        var credential = JSON.parse(localStorage.getItem(key));
        fetch('/api/user/handy-roles-type?iduser='+credential.id+'&tokenuser='+credential.token).then(res=>res.json()).then(res=>{
            this.role = res.projectProposer;
            this.$emit('load',false);
        });
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i]);
        }
    }
});