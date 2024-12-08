import { Product } from "@prisma/client";
import { ProductService } from "../services/product.service";
import { Request, Response } from "express";

export namespace ProductController {
  export async function getAllProducts(
    request: Request,
    response: Response<Product[]>
  ) {
    response.status(200).json(await ProductService.getAllProducts());
  }

  export async function getProductById(request: Request, response: Response) {
    response
      .status(200)
      .json(await ProductService.getProductById(parseInt(request.params.id)));
  }

  export async function createProduct(
    request: Request<Product>,
    response: Response<Product>
  ) {
    response.status(201).json(await ProductService.createProduct(request.body));
  }

  export async function updateProduct(
    request: Request,
    response: Response<Product>
  ) {
    response
      .status(200)
      .json(
        await ProductService.updateProduct(
          request.body,
          parseInt(request.params.id)
        )
      );
  }

  export async function deleteProduct(
    request: Request,
    response: Response<Product>
  ) {
    response
      .status(200)
      .json(await ProductService.deleteProduct(parseInt(request.params.id)));
  }
}
