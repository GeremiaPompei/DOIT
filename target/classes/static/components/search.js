export default Vue.component('search', {
    template: 
    /*html*/`
        <div style="margin: 10px; padding: 10%; padding-top: 1%">
            <div class="form-group">
                <input placeholder="Inserisci parola..." type="text" v-model="input" @input="search()" class="form-control">
            </div>
            <div class="btn-group btn-group-toggle" data-toggle="buttons" style="display: flex">
                <label class="btn btn-primary" :class="{'active': this.type=='users'}" style="flex: 1;">
                    <input type="radio" name="options" id="option1" autocomplete="off" checked="" @click="select('users')"> users
                </label>
                <label class="btn btn-primary" :class="{'active': this.type=='projects'}" style="flex: 1;">
                    <input type="radio" name="options" id="option2" autocomplete="off" @click="select('projects')"> projects
                </label>
            </div>
            <ul style="list-style-type: none;">
                <li v-for="(element, index) in elements" :key="index" style="padding-top: 10px">
                    <div @click="show(element.id)">
                        <button class="btn btn-outline-primary" style="width: 100%;">
                            {{element.name}}
                        </button>
                    </div>
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