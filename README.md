# Product Catalog

An Android Product Catalog application built as part of the Neurogine technical assessment.

The app consumes the DummyJSON Products API and provides product browsing, pagination, search, product details, and common loading, error, and empty states.

---

## Features

* Product list with thumbnail, title, and price
* Pagination using the API `skip` parameter
* Product detail screen
* Product description, price, rating, and images
* Debounced product search
* Loading state
* Error state with Retry
* Empty state
* Pull-to-refresh
* Image loading placeholder
* Image loading error handling
* List → Detail → Back navigation

---

## Tech Stack

* **Kotlin**
* **Jetpack Compose**
* **Material 3**
* **ViewModel**
* **StateFlow**
* **Coroutines**
* **Retrofit**
* **Gson**
* **Coil**
* **Navigation Compose**

---

## Architecture

The project uses a simple two-layer structure:
```text
data/
├── model/
├── remote/
└── repository/

ui/
├── productlist/
├── productdetail/
└── navigation/
```

### Data Layer

Responsible for:

* API communication
* Product models
* Repository operations

### UI Layer

Responsible for:

* Product list
* Product detail
* Search
* Pagination
* UI states
* Navigation

ViewModels manage UI state using StateFlow and communicate with the repository.

---

## API

The application uses the DummyJSON Products API.

### Product List

GET https://dummyjson.com/products?limit=20&skip=0

Pagination increases the skip value as more products are loaded.

### Product Detail

GET https://dummyjson.com/products/{id}

The selected product ID is passed as the path parameter.

### Product Search

GET https://dummyjson.com/products/search?q=phone

Search is performed server-side using the API search endpoint.

A 400 ms debounce is used to avoid making a request for every character typed.

---

## Search Approach

Server-side search was selected instead of filtering already-loaded products on the client.

This allows the application to search the complete product dataset instead of only the products currently loaded on the device.

Search results are treated separately from normal catalogue pagination.

---

## Pagination

The product list loads 20 products initially.

As the user approaches the end of the list, another request is made using the current skip value.

### Example

First request:
limit=20&skip=0

Second request:
limit=20&skip=20

Third request:
limit=20&skip=40

Pagination stops when the API returns fewer than 20 products.

---

## UI States

The application handles:

* Loading
* Success
* Empty
* Error
* Loading more
* Refreshing

Initial loading errors display a full-screen Retry state.

Pagination errors keep the existing products visible and provide a Retry action.

---

## Pull-to-Refresh

Pull-to-refresh reloads the first page of the normal product catalogue while keeping the existing products visible during the refresh operation.

---

## Image Handling

Remote images are loaded using Coil.

The application provides:

* Loading indicator while an image is loading
* Error message when an image fails to load
* Successfully loaded image display

This behavior is implemented on both the product list and product detail screens.

---

## How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Run the application on an Android emulator or physical device.

The application requires Internet access to communicate with the DummyJSON API.

---

## Testing

The following application flows were manually tested:

* Product list loading
* Pagination
* Search
* Product details
* Back navigation
* Pull-to-refresh
* Loading and error states
* Image loading

---

## AI Assistance

AI tools were used during development for implementation guidance, code suggestions, debugging assistance, and code review.

All generated suggestions were reviewed, integrated, tested, and verified as part of the final implementation.

---

## Author

Developed as part of the Neurogine Android technical assessment.
