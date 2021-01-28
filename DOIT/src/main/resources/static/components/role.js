export default Vue.component('role', {
    props: {
        role: {
            type: Object,
            required: true
        }
    },
    template: `
    <div class='container'>
        <p>Categorie</p>
        <ul>
            <li v-for="(category, index) in role.categories" key="index"">
                <h3>{{category.name}}</h3>
            </li>
        </ul>
    </div>
    `,
});