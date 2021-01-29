export default Vue.component('cerca', {
    template: `
        <div class='container'>
            <input placeholder="Inserisci parola..." type="text" v-model="input" @input="search()">
            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                <label class="btn btn-primary" :class="{'active': this.type=='users'}">
                    <input type="radio" name="options" id="option1" autocomplete="off" checked="" @click="select('users')"> users
                </label>
                <label class="btn btn-primary" :class="{'active': this.type=='projects'}">
                    <input type="radio" name="options" id="option2" autocomplete="off" @click="select('projects')"> projects
                </label>
            </div>
            <ul>
                <li v-for="(element, index) in elements" :key="index">
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