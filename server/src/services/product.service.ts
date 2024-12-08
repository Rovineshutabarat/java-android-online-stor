import { Product } from "@prisma/client";
import { prismaClient } from "../prisma.client";

export namespace ProductService {
  export async function getAllProducts(): Promise<Product[]> {
    return await prismaClient.product.findMany({
      include: {
        category: true,
      },
    });
  }

  export async function getProductById(id: number): Promise<Product> {
    return await prismaClient.product.findFirstOrThrow({
      where: {
        id: id,
      },
    });
  }

  export async function createProduct(product: Product): Promise<Product> {
    return await prismaClient.product.create({
      data: product,
    });
  }

  export async function updateProduct(
    product: Product,
    id: number
  ): Promise<Product> {
    return await prismaClient.product.update({
      where: {
        id: id,
      },
      data: product,
    });
  }

  export async function deleteProduct(id: number): Promise<Product> {
    return await prismaClient.product.delete({
      where: {
        id: id,
      },
    });
  }
}
