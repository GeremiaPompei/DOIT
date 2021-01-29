export default Vue.component('designer', {
    template: `
    <div class='container'>
        <role role="designer"></role>
        <ul>
            <li v-for="(el, index) in list" :key="index">
                <div @click="go(index)">
                    <button>{{el.name}}</button>
                </div>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            list: [
                {name: 'Send partecipation request to program manager', path: {path: '/send-pr/designer'}},
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        }
    }
});