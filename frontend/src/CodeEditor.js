import React, { useRef } from 'react';
import Editor from '@monaco-editor/react';
import axios from 'axios';

function CodeEditor({ problemId = 1, language = 'java', userId = 1 }) {
  const editorRef = useRef(null);

  const handleEditorMount = (editor) => {
    editorRef.current = editor;
  };

  const handleSubmit = async () => {
    const code = editorRef.current.getValue();

    try {
      const response = await axios.post('/api/submissions', {
        userId,
        problemId,
        code,
        language
      });

      alert(`
✅ Verdict: ${response.data.verdict}
⚡ Runtime: ${response.data.runtime}s
📦 Memory: ${response.data.memory}KB
📤 Output: ${response.data.output || "N/A"}
❗ Error: ${response.data.error || "None"}
      `);
    } catch (error) {
      console.error('Submission error:', error);
      alert('🚨 Submission failed. Check console for more info.');
    }
  };

  return (
    <div>
      <h2>Code Submission</h2>
      <Editor
        height="60vh"
        defaultLanguage={language}
        defaultValue="// Write your code here"
        onMount={handleEditorMount}
      />
      <button onClick={handleSubmit} style={{ marginTop: '10px' }}>
        Submit Code
      </button>
    </div>
  );
}

export default CodeEditor;
