import { Category } from "@prisma/client";
import { prismaClient } from "../prisma.client";

export namespace CategoryService {
  export async function getAllCategories(): Promise<Category[]> {
    return await prismaClient.category.findMany();
  }

  export async function getCategoryById(id: number): Promise<Category> {
    return await prismaClient.category.findFirstOrThrow({
      where: {
        id: id,
      },
    });
  }

  export async function createCategory(category: Category): Promise<Category> {
    return await prismaClient.category.create({
      data: category,
    });
  }

  export async function updateCategory(
    category: Category,
    id: number
  ): Promise<Category> {
    return await prismaClient.category.update({
      data: category,
      where: {
        id: id,
      },
    });
  }

  export async function deleteCategory(id: number): Promise<Category> {
    return await prismaClient.category.delete({
      where: {
        id: id,
      },
    });
  }
}
