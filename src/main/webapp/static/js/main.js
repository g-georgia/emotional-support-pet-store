
function addEventListeners() {
    const productCategories = document.querySelectorAll(".productCategory");
    for (let productCategory of productCategories) {
        productCategory.addEventListener("click", getProductsToProductCategory);
    }
}

function getProductsToProductCategory() {
    let productCategory = this.innerHTML;
    fetch(`/product-category?${productCategory}`)
        .then(response => response.json())
        .then(productsToList => buildDom(productsToList))
}

function buildDom(productsToList) {
    let products = "";
    for (let product of productsToList) {
        let productDetails = `
            <div class="col col-sm-12 col-md-6 col-lg-4">
                <div class="card">
                    <img class="image" src="/static/img/product_${product.id}.jpg" alt="${product.name}" />
                    <div class="card-header">
                        <h4 class="card-title">${product.name}</h4>
                        <p class="card-text">${product.description}</p>
                    </div>
                    <div class="card-body">
                        <div class="card-text">
                            <p class="lead">${product.defaultPrice} ${product.defaultCurrency}</p>
                        </div>
                        <div class="card-text">
                            <a class="btn btn-success" href="#">Add to cart</a>
                        </div>
                    </div>
                </div>
            </div>`

        products+=productDetails;
    }
    document.querySelector("#products").innerHTML=products;

}

addEventListeners();