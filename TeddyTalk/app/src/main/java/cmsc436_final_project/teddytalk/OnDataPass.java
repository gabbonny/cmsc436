package cmsc436_final_project.teddytalk;

// Callback interface that allows this Fragment to notify the
// StoryPromptActivity when user clicks selected input

interface OnDataPass {
    void onDataPass(String data, int index);
}