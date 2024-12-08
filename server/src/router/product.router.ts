import { Router } from "express";
import { CategoryController } from "../controllers/category.controller";
import { ProductController } from "../controllers/product.controller";

const ProductRouter = Router();

ProductRouter.get("/", ProductController.getAllProducts);
ProductRouter.get("/:id", ProductController.getProductById);
ProductRouter.post("/", ProductController.createProduct);
ProductRouter.put("/:id", ProductController.updateProduct);
ProductRouter.delete("/:id", ProductController.deleteProduct);

export default ProductRouter;
