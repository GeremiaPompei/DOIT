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
                {name: 'Manage project state', path: {path: '/manage-project-state'}},
                {name: 'Evaluate designers', path: {path: '/evaluate'}}
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        }
    }
});