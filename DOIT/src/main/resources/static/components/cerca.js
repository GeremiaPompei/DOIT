export default Vue.component('cerca', {
    template: `
        <div class='container'>
            <input placeholder="Inserisci parola..." type="text" v-model="input" class="el" @input="search()">
            <button class="el" @click="select('users')">Users</button>
            <button class="el" @click="select('projects')">Projects</button>
            <ul>
                <li v-for="(element, index) in elements" :key="index" class="el">
                    <button @click="show(element.id)">{{element.name}}</button>
                </li>
            </ul>
        </div>
        `,
    data() {
        return {
            input: '',
            type: 'users',
            elements: []
        }
    },
    methods: {
        async search() {
            if(this.input!='') {
                this.$emit('load', true);
                this.elements = await (await fetch('/api/search/'+this.type+'-by-key?key='+this.input)).json();
                this.$emit('load', false);
            } else {
                this.elements = [];
            }
        },
        select(type) {
            this.type = type;
            this.search();
        },
        show(id) {
            if(this.type=='projects') {
                this.$router.push({path: '/project/'+id});
            }
            if(this.type=='users') {
                this.$router.push({path: '/user/'+id});
            }
        }
    }
});