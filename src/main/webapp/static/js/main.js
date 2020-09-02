function addEventListeners() {
    const productCategories = document.querySelectorAll(".productCategory");
    for (let productCategory of productCategories) {
        productCategory.addEventListener("click", getProductsToProductCategory);
    }

    const suppliers = document.querySelectorAll(".supplier");
    for (let supplier of suppliers) {
        supplier.addEventListener("click", getProductsToSupplier);
    }

    const addToCartButtons = document.querySelectorAll(".btn.btn-success");
    for (let button of addToCartButtons) {
        button.addEventListener("click", getSelectedProduct)

    }
}

function getProductsToSupplier() {
    let supplier = this.innerHTML;
    fetch(`/supplier?${supplier}`)
        .then(response => response.json())
        .then(productsToList => buildDom(productsToList))
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

        products += productDetails;
    }
    document.querySelector("#products").innerHTML = products;
    const addToCartButtons = document.querySelectorAll(".btn.btn-success");
    for (let button of addToCartButtons) {
        if (!(button.classList.contains("hasEventListener"))) {
            button.addEventListener("click", getSelectedProduct)
        }

    }
}

function getSelectedProduct() {
    this.classList.add("hasEventListener");
    let selectedElement = this.parentElement.parentElement.parentElement.children[1].children[0].innerText;
    fetch(`/order/product?${selectedElement}`)
        .then(response => response.json())
        .then(orderList => something(orderList))
}

function something(orderList) {
    console.log(orderList);
}

addEventListeners();