export default Vue.component('project-proposer', {
    template: `
    <div class='container'>
        <role role="project-proposer"></role>
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
                {name: 'Create project', path: {path: '/create-project'}},
                {name: 'Manage partecipation request program managar', path: {path: '/manage-pr/project-proposer'}}
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        }
    }
});