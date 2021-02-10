export default Vue.component('send-pr', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
        <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
        <div class="form-group" style="margin-top: 10px">
            <select @change="onChange($event)" class="form-control">
                <option key="-1" value="-1">---</option>
                <option v-for="(category, index) in categories" :value="index" :key="index">
                    {{category.name}}
                </option>
            </select>
        </div>
        <div v-for="(element, index) in projects" :key="index">
            <div class="card border-primary mb-3" style="margin-top: 10px">
                <div class="card-header" style="text-align: center">
                    <button class="btn btn-outline-info" style="width: 100%; margin-bottom: 5px" @click="show(element.id)">{{element.name}}</button>
                </div>
                <div class="card-body">
                    <button type="button" class="btn btn-outline-primary" style="width: 100%" @click="sendPr(element.id)">send</button>
                </div>
            </div>
        </div>    
    </div>
    `,
    data() {
        return {
            categories: [],
            projects: [],
            role: this.$route.params.role
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.categories = await (await fetch('/api/user/list-categories?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
            this.$emit('load',false);
        },
        async sendPr(index) {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/'+this.role+'/send-pr?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+index, {method: 'POST'})).text();
            await this.init();
            this.projects = [];
            this.$emit('load',false);
            this.$emit('push', res);
        },
        back() {
            this.$router.go(-1);
        },
        show(id) {
            this.$router.push({path: '/project/'+id});
        },
        async onChange(event) {
            var index = event.target.value;
            if(index>=0) {
                this.$emit('load',true);
                var category = this.categories[event.target.value];
                var credential = JSON.parse(localStorage.getItem(key));
                this.projects = await (await fetch('/api/'+this.role+'/list-projects-by-category?iduser='+credential.id+'&tokenuser='+credential.token+'&idcategory='+category.name)).json();
                this.$emit('load',false);
            }
        }
    }
});