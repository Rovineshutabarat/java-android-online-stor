import express from "express";
import CategoryRouter from "./router/category.router";
import ProductRouter from "./router/product.router";

const app = express();
app.use(express.json());

app.use("/category", CategoryRouter);
app.use("/product", ProductRouter);

app.listen(3000, () => {
  console.log("Server is running");
});
