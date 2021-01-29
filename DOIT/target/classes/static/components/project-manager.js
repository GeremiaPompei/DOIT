export default Vue.component('project-manager', {
    template: `
    <div class='container'>
        <role role="project-manager"></role>
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
                {name: 'Upgrade state', path: {path: '/upgrade-state'}}
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        }
    }
});