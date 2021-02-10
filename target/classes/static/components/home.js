export default Vue.component('home', {
    template: 
    /*html*/`
    <div style="display: flex; flex-wrap: wrap; align-items: center; justify-content: center;">
        <div style="width: 500px; margin: 1rem;">
            <img class="image" style="width: 100%; border: 1px solid #999" src="img.jpg">
        </div>
        <div style="width: 500px; margin: 3rem;">
            <h1>DOIT</h1>
            <h4>What it offers:</h4>
            <ul>
                <li>Easy ways to create a project</li>
                <li>Easy ways to organize your team</li>
                <li>Easy access to all the information you need about your co-workers</li>
            </ul>
            <p class="lead">
                <a class="btn btn-primary btn-lg" href="https://github.com/GeremiaPompei/DOIT" role="button">Learn
                    more</a>
            </p>
        </div>
    </div>
    `
})