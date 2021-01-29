export default Vue.component('role', {
    props: {
        role: {
            type: String,
            required: true
        }
    },
    template: `
    <div class='container'>
        <ul>
            <li v-for="(el, index) in list" :key="index">
                <button @click="go(index)">
                    {{el.name}}
                </button>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            list: [
                {name: 'List projects', path: {path: '/list-projects/'+this.role}},
                {name: 'History', path: {path: '/history/'+this.role}},
                {name: 'List categories', path: {path: '/list-categories/'+this.role}}
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        }
    }
});