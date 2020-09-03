export interface IAttachment {
  id?: number;
  name?: string;
  contentId?: string;
  contentLength?: number;
  mimeType?: string;
}

export class Attachment implements IAttachment {
  constructor(
    public id?: number,
    public name?: string,
    public contentId?: string,
    public contentLength?: number,
    public mimeType?: string
  ) {}
}
