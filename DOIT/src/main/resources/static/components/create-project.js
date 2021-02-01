export default Vue.component('create-project', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
        <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
        <form @submit.prevent="createProject()">
            <div class="form-group" style="margin-top: 10px">
                <input placeholder="Project name..." type="text" v-model="name" class="form-control">
            </div>
            <div class="form-group">
                <input placeholder="Project description..." type="text" v-model="description" class="form-control">
            </div>
            <div class="form-group">
                <select @change="onChange($event)" class="form-control">
                    <option key="-1" value="-1">---</option>
                    <option v-for="(category, index) in categories" :value="index" :key="index">
                        {{category.name}}
                    </option>
                </select>
            </div>   
            <input class="btn btn-outline-primary" style="width: 100%" type="submit" value="Crea Progetto">
        </form>
    </div>
    `,
    data() {
        return {
            role: undefined,
            name: '',
            description: '',
            categories: [],
            categoryIndex: -1
        }
    },
    async created() {
        this.$emit('load',true);
        var credential = JSON.parse(localStorage.getItem(key));
        this.categories = await (await fetch('/api/user/list-categories?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole=project-proposer')).json();
        this.$emit('load',false);
    },
    methods: {
        async createProject() {
            if(this.name=='' || this.description=='')
                this.$emit('push', 'Filds missed!');
            else {
                if(this.categoryIndex<0) {
                    this.$emit('push', "Seleziona una categoria!");
                } else {
                    this.$emit('load',true);
                    var credential = JSON.parse(localStorage.getItem(key));
                    var res = await (await fetch('/api/project-proposer/create-project?iduser='+credential.id+'&tokenuser='+credential.token+
                    '&name='+this.name+'&description='+this.description+'&idcategory='+this.categories[this.categoryIndex].name, {
                        method: 'POST'})).text();
                    this.$emit('load',false);
                    this.$emit('push', res);
                    this.$router.go(-1);
                }
            }
        },
        back() {
            this.$router.go(-1);
        },
        onChange(event) {
            this.categoryIndex = event.target.value;
        }
    }
});