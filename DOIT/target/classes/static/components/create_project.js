export default Vue.component('create-project', {
    template: `
    <div class='container'>
        <button @click="back()" class="back"><i style="font-size:24px" class="fa">&#xf104;</i></button>
        <form @submit.prevent="">
            <p>Name</p>
            <input placeholder="Project name..." type="text">
            <p>Description</p>
            <input placeholder="Project description..." type="text">
            <p>Category</p>
            <select>
            <option v-for="(category, index) in role.categories" key="index"">
                {{category.name}}
            </option>
            </select>
            <input type="submit" value="Crea Progetto">
        </form>
    </div>
    `,
    data() {
        return {
            role: undefined
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
        back() {
            this.$router.go(-1);
        }
    }
});