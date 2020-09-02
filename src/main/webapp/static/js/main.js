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

    const productCategoryFilter = document.querySelector(".filter-product-category")
    productCategoryFilter.addEventListener("click", showProductCategories);
    productCategoryFilter.addEventListener("mouseover", changeBackgroundColorBlack);
    productCategoryFilter.addEventListener("mouseout", changeBackgroundColorWhite);

    const supplierFilter = document.querySelector(".filter-supplier")
    supplierFilter.addEventListener("click", showSuppliers);
    supplierFilter.addEventListener("mouseover", changeBackgroundColorBlack);
    supplierFilter.addEventListener("mouseout", changeBackgroundColorWhite);
}

function changeBackgroundColorBlack() {
    this.style.backgroundColor = "black";
    this.style.color = "white";

}

function changeBackgroundColorWhite() {
    this.style.backgroundColor = "white";
    this.style.color = "black";
}

function showSuppliers() {
    let options = document.querySelectorAll(".hidden-supplier");
    if (options.length !== 0) {
        for (let option of options) {
            option.classList.remove("hidden-supplier");
        }
    } else {
        let optionsToHide = document.querySelectorAll(".supplier-options");
        for (let option of optionsToHide) {
            option.classList.add("hidden-supplier");
        }
    }

}

function showProductCategories() {
    let options = document.querySelectorAll(".hidden-category");
    if (options.length !== 0) {
        for (let option of options) {
            option.classList.remove("hidden-category");
        }
    } else {
        let optionsToHide = document.querySelectorAll(".product-category");
        for (let option of optionsToHide) {
            option.classList.add("hidden-category");
        }
    }


}

function getProductsToSupplier() {
    let supplierId = this.id;
    fetch(`/supplier?${supplierId}`)
        .then(response => response.json())
        .then(productsToList => buildDom(productsToList))
}


function getProductsToProductCategory() {
    let productCategoryId = this.id;
    fetch(`/product-category?${productCategoryId}`)
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
                        <h4 class="card-title" id="${product.id}">${product.name}</h4>
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
    let selectedElementId = this.parentElement.parentElement.parentElement.children[1].children[0].id;
    fetch(`/order/product?${selectedElementId}`)
        .then(response => response.json())
        .then(orderList => something(orderList))
}

function something(orderList) {
    console.log(orderList);
}

addEventListeners();