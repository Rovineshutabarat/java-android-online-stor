import { Router } from "express";
import { CategoryController } from "../controllers/category.controller";

const CategoryRouter = Router();

CategoryRouter.get("/", CategoryController.getAllCategories);
CategoryRouter.get("/:id", CategoryController.getCategoryById);
CategoryRouter.post("/", CategoryController.createCategory);
CategoryRouter.put("/:id", CategoryController.updateCategory);
CategoryRouter.delete("/:id", CategoryController.deleteCategory);

export default CategoryRouter;
