import { Category } from "@prisma/client";
import { CategoryService } from "../services/category.service";
import { Request, Response } from "express";

export namespace CategoryController {
  export async function getAllCategories(
    request: Request,
    response: Response<Category[]>
  ) {
    response.status(200).json(await CategoryService.getAllCategories());
  }

  export async function getCategoryById(
    request: Request,
    response: Response<Category>
  ) {
    response
      .status(200)
      .json(await CategoryService.getCategoryById(parseInt(request.params.id)));
  }

  export async function createCategory(
    request: Request,
    response: Response<Category>
  ) {
    response
      .status(201)
      .json(await CategoryService.createCategory(request.body));
  }

  export async function updateCategory(
    request: Request,
    response: Response<Category>
  ) {
    response
      .status(200)
      .json(
        await CategoryService.updateCategory(
          request.body,
          parseInt(request.params.id)
        )
      );
  }

  export async function deleteCategory(
    request: Request,
    response: Response<Category>
  ) {
    response
      .status(200)
      .json(await CategoryService.deleteCategory(parseInt(request.params.id)));
  }
}
