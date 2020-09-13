export interface IAttachmentType {
  id?: number;
  code?: string;
  name?: string;
}

export class AttachmentType implements IAttachmentType {
  constructor(public id?: number, public code?: string, public name?: string) {}
}
