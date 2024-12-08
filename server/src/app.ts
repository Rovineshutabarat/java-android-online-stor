import express from "express";
import CategoryRouter from "./router/category.router";

const app = express();
app.use(express.json());

app.use("/category", CategoryRouter);

app.listen(3000, () => {
  console.log("Server is running");
});
